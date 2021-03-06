import au.com.bytecode.opencsv.CSVReader
import groovy.xml.MarkupBuilder

/**
 * @author Andrew Young <andrew at vaelen.org>
 */
class CSVConverter {

    static SEMANTIC_FIELDS = [:]
    static CATEGORIES = [:]
    static DEFAULT_CATEGORY = "OTHER"

    /**
     * Convert all of the CSV files in the csv directory to XML files in the
     * xml directory.
     */
    static convertCSV() {
        new File("csv").eachDir { dir ->
            def type = dir.name
            def list = []
            new File("csv/${type}/").eachFileMatch(~/.*\.csv/) { file -> list << file.name.replaceAll(/\.csv/, '') }
            convertCSV(type, list)
        }
    }

    /**
     * Convert the given list of CSV files (without extensions) in the csv
     * directory to XML files in the xml directory.
     */
    static convertCSV(type, files) {
        parseSemanticFields()
        def convert = null
        switch(type) {
            case "lexical":
                convert = {inFile, outFile -> convertLexicalCSV(inFile, outFile) }
                break;
            case "grammatical":
                convert = {inFile, outFile -> convertGrammaticalCSV(inFile, outFile) }
                break;
            default:
                println "Unknown CSV Type: ${type}"
                break;
        }
        if(convert) {
            files.each { name ->
                File inFile = new File("csv/${type}/${name}.csv")
                File outFile = new File("xml/${type}/${name}.xml")
                outFile.parentFile.mkdirs()
                println "Converting '${type}' CSV: ${inFile} -> ${outFile}}"
                convert(inFile, outFile)
            }
        }
    }

    private static isEmptyRow(row) {
        boolean ret = true
        if(row) {
            row.each { value ->
                if(value) {
                    ret = false
                }
            }
        }
        return ret
    }

    static convertGrammaticalCSV(File input, File output) {
        def languageList = null
        def languageFamilyList = null
        def personEnteringDataList = null

        def features = []

        String fileName = input.name.replaceAll(/\.[^\.]+$/, '');

        // First parse CSV
        input.withReader('UTF-8') { reader ->
            def csvReader = new CSVReader(reader)
            csvReader.readAll().each{ csvrow ->
                def first = csvrow[0].trim()
                def second = csvrow[1].trim()
                def third = csvrow[2].trim()
                def data = csvrow[3..csvrow.length-1].collect { it.trim() }

                def records = []

                if(!languageList) {
                    languageList = data
                } else if (!languageFamilyList) {
                    languageFamilyList = data
                } else if (!personEnteringDataList) {
                    personEnteringDataList = data
                } else {
                    if (isEmptyRow(data)) {
                        // Separator
                    } else if (first == 'Feature') {
                        // Header Row
                    } else {
                        def featureName = first
                        def featureDefinition = second
                        def featureData = []

                        def language = null
                        def languageFamily = null
                        def personEnteringData = null
                        def featureValue = null
                        def source = null
                        def comments = null
                        data.eachWithIndex { value, i ->
                            if(!featureValue) {
                                language = languageList[i]
                                languageFamily = languageFamilyList[i]
                                personEnteringData = personEnteringDataList[i]
                                featureValue = value
                            } else if (!source) {
                                source = value
                            } else {
                                comments = value
                                featureData << [
                                    'language':language,
                                    'languageFamily':languageFamily,
                                    'personEnteringData':personEnteringData,
                                    'value':featureValue,
                                    'source':source,
                                    'comments':comments
                                ]

                                language = null
                                languageFamily = null
                                personEnteringData = null
                                featureValue = null
                                source = null
                                comments = null
                            }
                        }

                        features << [
                            'name':featureName,
                            'definition':featureDefinition,
                            'data':featureData
                        ]
                    }
                }
            }
        }

        // Then build XML
        output.withWriter('UTF-8') { writer ->
            def builder = new MarkupBuilder(writer)
            builder.features('type':'grammatical') {
                features.each { f ->
                    feature {
                        meta {
                            field(name:'name', f['name'])
                            field(name:'definition', f['definition'])
                        }
                        f['data'].each {d ->
                            data {
                                d.each { fieldKey, fieldValue -> field(name:fieldKey, fieldValue) }
                            }
                        }
                    }
                }
            }
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
    static convertLexicalCSV(File input, File output) {
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
                            records[i]['meta'] = ['comments':'']
                            metadata[i].each { metaKey, metaValue ->
                                switch(metaKey) {
                                    case "English":
                                        records[i]['meta']['englishHeadword'] = metaValue
                                        break
                                    case "Portuguese":
                                        records[i]['meta']['portugueseHeadword'] = metaValue
                                        break
                                    case "Spanish":
                                        records[i]['meta']['spanishHeadword'] = metaValue
                                        break
                                    case "Latin":
                                        records[i]['meta']['latinHeadword'] = metaValue
                                        break
                                    default:
                                        records[i]['meta']['comments'] += metaValue + "\n"
                                        break
                                }
                            }
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
            builder.features('type':'lexical') {
                records.each { record ->
                    feature {
                        meta {
                            record['meta'].each {metaKey, metaValue -> field(name:metaKey, metaValue ) }
                        }
                        record['data'].each {d ->
                            data {
                                d.each { fieldKey, fieldValue -> field(name:fieldKey, fieldValue) }
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
}

