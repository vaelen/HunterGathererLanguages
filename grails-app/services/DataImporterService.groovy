import au.com.bytecode.opencsv.CSVReader
import groovy.xml.MarkupBuilder
import groovy.util.XmlParser

/**
 * This class imports data from an XML file.
 * @author Andrew Young <andrew at vaelen.org>
 */
class DataImporterService {

    boolean transactional = true

    def sessionFactory

    static SEMANTIC_FIELDS = [:]
    static CATEGORIES = [:]
    static DEFAULT_CATEGORY = "OTHER"

    /**
     * Convert all of the CSV files in the csv directory to XML files in the
     * xml directory.
     */
    static convertCSV() {
        def files = []
        new File("csv").eachFileMatch(~/.*\.csv/) { file -> files << file.name.replaceAll(/\.csv/, '') }
        convertCSV(files)
    }

    /**
     * Convert the given list of CSV files (without extensions) in the csv
     * directory to XML files in the xml directory.
     */
    static convertCSV(files) {
        parseSemanticFields()
        files.each { name ->
            convertCSV(new File("csv/${name}.csv"), new File("xml/${name}.xml"))
        }
    }

    /**
     * CSV files for HunterGathererLanguages data are column based instead
     * of row based.  Each column starting from the 4th column contains data
     * on a single word.  The first column is the language family, the second
     * column is the language, the third column is the source of the data.
     * Each row/column combination is an entry after that.
     * The beginning of each file begins with a set of meta-data about the
     * words that are shared between all entries.  These are marked by an
     * empty first column.
     */
    static convertCSV(File input, File output) {
        def metadata = []
        def records = []

        String fileName = input.name.replaceAll(/\.[^\.]+$/, '');

        // First parse CSV
        input.withReader('UTF-8') { reader ->
            def csvReader = new CSVReader(reader)
            boolean header = true
            boolean metaSet = false
            def lastLanguageFamily = null
            def lastLanguage = null
            def lastRow = null

            csvReader.readAll().each{ csvrow ->

                // csvrow is a String[]
                def first = csvrow[0].trim()
                def second = csvrow[1].trim()
                def third = csvrow[2].trim()
                def data = csvrow[3..csvrow.length-1].collect { it.trim() }
                if (header) {
                    for(i in  1..data.size) {
                        metadata << [:]
                        records << ['data':[]]
                    }
                    header = false
                }
                if (!first) {
                    // This is a piece of meta-data
                    def name = third
                    data.eachWithIndex() { value, i -> metadata[i][name] = value }
                } else {

                    def currentRow = []
                    for(i in 1..data.size) {
                        currentRow << [:]
                    }

                    def languageFamily = first
                    def language = second
                    def source = third
                    data.eachWithIndex() { value, i ->
                        if(!metaSet) {
                            records[i]['meta'] = metadata[i]
                            records[i]['meta']['semanticField'] = getSemanticField(metadata[i]['English'], fileName)
                            records[i]['meta']['category'] = getCategory(metadata[i]['English'])
                        }
                        if(value) {
                            if(currentRow) {
                                currentRow[i] = [
                                    'languageFamily':languageFamily,
                                    'language':language,
                                    'source':source,
                                    'originalForm':value
                                ]
                            }

                            if(lastRow) {
                                // Second Row
                                if(languageFamily == lastLanguageFamily && language == lastLanguage) {
                                    // Phonemic Form Of First Row
                                    lastRow[i]['phonemicizedForm'] = value
                                    // Ignore current row
                                    currentRow = null
                                }
                            }
                        }
                    }
                    if(!metaSet) {
                       metaSet = true
                    }
                    // Output last row
                    lastRow.eachWithIndex() { value, i ->
                        if(value) {
                            if(!value['phonemicizedForm']) {
                                value['phonemicizedForm'] = 'NONE'
                            }
                            records[i]['data'] << value
                        }
                    }
                    lastRow = currentRow
                    lastLanguageFamily = languageFamily
                    lastLanguage = language
                }
            }
            if(lastRow) {
                lastRow.eachWithIndex() { value, i ->
                    if(value) {
                        if(!value['phonemicizedForm']) {
                            value['phonemicizedForm'] = 'NONE'
                        }
                        records[i]['data'] << value
                    }
                }
            }
        }

        records = records.findAll { record -> record }

        // Then create XML
        output.withWriter('UTF-8') { writer ->
            def builder = new MarkupBuilder(writer)
            builder.entries {
                records.each { record ->
                    entry {
                        meta {
                            record['meta'].each {field -> "$field.key"(field.value) }
                        }
                        record['data'].each {d ->
                            item {
                                d.each { field -> "$field.key"(field.value) }
                            }
                        }
                    }
                }
            }
        }
    }

    static synchronized parseSemanticFields() {
        if(!SEMANTIC_FIELDS) {
            parseSemanticFields(new File("semanticFields.tab"))
        }
    }

    static synchronized parseSemanticFields(File file) {
        if(!SEMANTIC_FIELDS) {
            file.withReader() { reader ->
                reader.eachLine() { line ->
                    def parts = line.split("\t")
                    SEMANTIC_FIELDS[parts[0]] = parts[1]
                    CATEGORIES[parts[0]] = parts[2]
                }
            }
        }
    }

    static String getSemanticField(word, fallback) {
        def semanticField = SEMANTIC_FIELDS[word]
        if(!semanticField) {
            semanticField = fallback
        }
        return semanticField
    }

    static String getCategory(word) {
        def category = CATEGORIES[word]
        if(!category) {
            category = DEFAULT_CATEGORY
        }
        return category
    }

    String getValueFromXMLElement(element) {
        def value = element.value.flatten()[0]
        if(value) {
            return value
        } else {
            return ''
        }
    }

    def importLexicalXML() {
        def files = []
        new File("xml").eachFileMatch(~/.*\.xml/) { file -> files << file }
        importLexicalXML(files)
    }

    def importLexicalXML(files) {
        files.each { file ->
            importLexicalXML(file)
        }
    }

    def importLexicalXML(File input) {

        def createdObjects = []

        def outputBuffer = new StringWriter()
        def output = new PrintWriter(outputBuffer)

        def logOutput = { line ->
            println line
            output.println line
            output.flush()
        }

        def entries = new XmlParser().parse(input)
        logOutput("Entries: ${entries}")

        def pos = PartOfSpeech.findByName("Unknown")
        logOutput("Part Of Speech: ${pos}")

        def caseStudyRegion = CaseStudyRegion.findByName("All")
        logOutput("Case Study Region: ${caseStudyRegion}")

        entries.entry.each() { entry ->
            def meta = entry.meta

            def english = getValueFromXMLElement(meta.English)
            def portuguese = getValueFromXMLElement(meta.Portuguese)
            def spanish = getValueFromXMLElement(meta.Spanish)
            def latin = getValueFromXMLElement(meta.Latin)
            def comments = getValueFromXMLElement(meta.Distribution) + "\n" + getValueFromXMLElement(meta.Comments)
            def semanticFieldName = getValueFromXMLElement(meta.semanticField)
            def categoryName = getValueFromXMLElement(meta.category)

            entry.item.each { item ->

                def languageFamilyName = getValueFromXMLElement(item.languageFamily)
                def languageName = getValueFromXMLElement(item.language)
                def sourceName = getValueFromXMLElement(item.source)
                def originalForm = getValueFromXMLElement(item.originalForm)
                def phonemicizedForm = getValueFromXMLElement(item.phonemicizedForm)

                def lexicalFeature = createLexicalFeature(english, portuguese, spanish, latin, comments, pos, caseStudyRegion, semanticFieldName, categoryName, logOutput);
                def language = createSourceLanguage(languageFamilyName, languageName, caseStudyRegion, logOutput)

                def lexicalData = LexicalData.findByLexicalFeatureAndSourceLanguage(lexicalFeature, language)

                if(!lexicalData) {
                    lexicalData = new LexicalData(
//                        'lexicalFeature': lexicalFeature,
//                        'sourceLanguage': language,
                        'originalForm': originalForm,
                        'phonemicizedForm': phonemicizedForm,
                        'etymologyNotes': '',
                        'semanticNotes': '',
                        'phonologyNotes': '',
                        'grammaticalNotes': '',
                        'generalNotes': ''
                    )
                    if(lexicalFeature) {
                        lexicalData.lexicalFeature = lexicalFeature
                    }
                    if(language) {
                        lexicalData.sourceLanguage = language
                    }

                    if(!lexicalData.save()) {
                        lexicalData.errors.each { error ->
                            logOutput("Error Saving Lexical Data: ${error}")
                        }
                        lexicalData = null
                    } else {
                        logOutput("Created Lexical Data: ${lexicalData}")
                    }

                    if(lexicalData) {
                        createdObjects << lexicalData
                    }
                }

            }
        }

        def lexicalDataList = LexicalData.list()

        logOutput("Lexical Feature Count: ${LexicalFeature.list().size()}")

        logOutput("Created Object Count: ${createdObjects.size()}")
        logOutput("Lexical Data Count: ${lexicalDataList.size()}")

        return ['output':outputBuffer.toString(), 'lexicalDataList':lexicalDataList, 'createdObjects':createdObjects]
    }

    def createSourceLanguage(languageFamilyName, languageName, caseStudyRegion, logOutput) {
        println "createSourceLanguage(languageFamilyName: ${languageFamilyName}, languageName: ${languageName}, caseStudyRegion: ${caseStudyRegion})"
        def language = SourceLanguage.findByFamilyAndName(languageFamilyName, languageName)
        if(!language) {
            language = new SourceLanguage(
                'family': languageFamilyName,
                'name': languageName,
//                'caseStudyRegion': caseStudyRegion,
                'isoCode': 'xxx',
                'otherNames': '',
                'subGroup': '',
                'longitude': 0,
                'latitude': 0,
                'notes': ''
            )
            language.caseStudyRegion = caseStudyRegion
            if(!language.save()) {
                language.errors.each { error ->
                    logOutput("Error Saving Source Language: ${error}")
                }
                language = null
            } else {
                logOutput("Created Source Language: ${language}")
            }
        }
        return language
    }

    /**
     * Create a given LexicalFeature object if it doesn't already exist.
     * Otherwise return it.
     */
    def createLexicalFeature(english, portuguese, spanish, latin, comments, pos, caseStudyRegion, semanticFieldName, categoryName, logOutput) {
        def lexicalFeature = LexicalFeature.findByEnglishHeadword(english)
        if(!lexicalFeature) {
            def semanticField = createSemanticField(semanticFieldName, logOutput)
            def category = createLexicalFeatureCategory(categoryName, logOutput)
            lexicalFeature = new LexicalFeature(
                'englishHeadword':english,
                'portugueseHeadword': portuguese,
                'spanishHeadword': spanish,
                'latinHeadword': latin,
                'frenchHeadword': '',
                'partOfSpeech':pos,
                'caseStudyRegion': caseStudyRegion,
                'comments': comments
            )
            if(semanticField) {
                lexicalFeature.semanticField = semanticField
            }
            if(category) {
                lexicalFeature.category = category
            }
            if(!lexicalFeature.save()) {
                lexicalFeature.errors.each { error ->
                    logOutput("Error Saving Lexical Feature: ${error}")
                }
                lexicalFeature = null
            } else {
                logOutput("Created Lexical Feature: ${lexicalFeature}")
            }
        }
        return lexicalFeature
    }

    /**
     * Create a given LexicalFeatureCategory object if it doesn't already exist.
     * Otherwise return it.
     */
    def createLexicalFeatureCategory(name, logOutput) {
        def category = LexicalFeatureCategory.findByName(name)
        if(!category) {
            category = new LexicalFeatureCategory('name':name)
            if(!category.save()) {
                category.errors.each { error ->
                    logOutput("Error Saving Lexical Feature Category: ${error}")
                }
                category = null
            } else {
                logOutput("Created Lexical Feature Category: ${category}")
            }
        }
        return category
    }

     /**
     * Create a given SemanticField object if it doesn't already exist.
     * Otherwise return it.
     */
    def createSemanticField(name, logOutput) {
        def semanticField = SemanticField.findByName(name)
        if(!semanticField) {
            semanticField = new SemanticField('name':name)
            if(!semanticField.save()) {
                semanticField.errors.each { error ->
                    logOutput("Error Saving Semantic Field: ${error}")
                }
                semanticField = null
            } else {
                logOutput("Created Semantic Field: ${semanticField}")
            }
        }
        return semanticField
    }
}

