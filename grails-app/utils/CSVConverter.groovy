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
        new File("csv").eachDir { dir -> files[dir.name] = []
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
        files.each { name ->
            File inFile = new File("csv/${type}/${name}.csv")
            File outFile = new File("xml/${type}/${name}.xml")
            outFile.mkdirs()
            switch(type) {
                case "lexical":
                    convertLexicalCSV(inFile, outFile)
                    break;
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
            builder.entries('type':'lexical') {
                records.each { record ->
                    entry {
                        meta {
                            record['meta'].each {metaKey, metaValue -> field(name:metaKey, metaValue ) }
                        }
                        record['data'].each {d ->
                            item {
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
