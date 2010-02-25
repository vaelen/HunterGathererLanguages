import au.com.bytecode.opencsv.CSVReader
import groovy.xml.MarkupBuilder
import groovy.util.XmlParser

/**
 * This class imports data from an XML file.
 * @author Andrew Young <andrew at vaelen.org>
 */
class DataImporterService {

    boolean transactional = true

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
        parseSemanticFields(new File("semanticFields.tab"))
    }

    static synchronized parseSemanticFields(File file) {
        file.withReader() { reader ->
            reader.eachLine() { line ->
                def parts = line.split("\t")
                SEMANTIC_FIELDS[parts[0]] = parts[1]
                CATEGORIES[parts[0]] = parts[2]
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

    static String getValueFromXMLElement(element) {
        return element.value.flatten()[0]
    }

    static importLexicalXML() {
        def files = []
        new File("xml").eachFileMatch(~/.*\.xml/) { file -> files << file }
        importLexcialXML(files)
    }

    static importLexicalXML(files) {
        files.each { file ->
            importLexcialXML(file)
        }
    }

    static importLexicalXML(File input) {
        def entries = new XmlParser().parse(input)
        println entries

        def pos = PartOfSpeech.findByName("Unknown")
        println pos

        def caseStudyRegion = PartOfSpeech.findByName("All")
        println caseStudyRegion

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

                def lexicalFeature = createLexicalFeature(english, portuguese, spanish, latin, comments, pos, caseStudyRegion, semanticFieldName, categoryName);
                println lexicalFeature

                def language = createSourceLanguage(languageFamilyName, languageName, caseStudyRegion)
                println language

                new LexicalData(
                    lexicalFeature: lexicalFeature,
                    sourceLanguage: language,
                    originalForm: originalForm,
                    phonemicizedForm: phonemicizedForm
                ).save()

            }
        }
        LexicalData.list()
    }

    static createSourceLanguage(languageFamilyName, languageName, caseStudyRegion) {
        def language = SourceLanguage.findByFamilyAndName(languageFamilyName, languageName)
        if(!language) {
            language = new SourceLanguage(
                family: languageFamilyName,
                name: languageName,
                caseStudyRegion: caseStudyRegion,
                isoCode: 'xxx'
            ).save()
        }
        return language
    }

    /**
     * Create a given LexicalFeature object if it doesn't already exist.
     * Otherwise return it.
     */
    static createLexicalFeature(english, portuguese, spanish, latin, comments, pos, caseStudyRegion, semanticFieldName, categoryName) {
        def lexicalFeature = LexicalFeature.findByEnglishHeadword(english)
        if(!lexicalFeature) {
            def semanticField = createSemanticField(semanticFieldName)
            def category = createLexicalFeatureCategory(categoryName)
            lexicalFeature = new LexicalFeature(
                englishHeadword:english,
                portugueseHeadword: portuguese,
                spanishHeadword: spanish,
                latinHeadword: latin,
                partOfSpeech:pos,
                semanticField:semanticField,
                category:category,
                caseStudyRegion: caseStudyRegion
            ).save()
        }
        return lexicalFeature
    }

    /**
     * Create a given LexicalFeatureCategory object if it doesn't already exist.
     * Otherwise return it.
     */
    static createLexicalFeatureCategory(name) {
        def category = LexicalFeatureCategory.findByName(name)
        if(!category) {
            category = new LexicalFeatureCategory(name:name).save()
        }
        return category
    }

     /**
     * Create a given SemanticField object if it doesn't already exist.
     * Otherwise return it.
     */
    static createSemanticField(name) {
        def semanticField = SemanticField.findByName(name)
        if(!semanticField) {
            semanticField = new SemanticField(name:name).save()
        }
        return semanticField
    }
}

