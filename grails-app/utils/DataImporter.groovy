import au.com.bytecode.opencsv.CSVReader
import groovy.xml.MarkupBuilder

/**
 * This class imports data from an XML file.
 * @author Andrew Young <andrew at vaelen.org>
 */
class DataImporter {

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
            csvReader.readAll().each{ csvrow ->
                // csvrow is a String[]
                def first = csvrow[0].trim()
                def second = csvrow[1].trim()
                def third = csvrow[2].trim()
                def data = csvrow[3..csvrow.length-1].collect { it.trim() }
                if (header) {
                    for(i in  1..data.size) {
                        metadata << [:]
                        records << [:]
                    }
                    header = false
                }
                if (!first) {
                    // This is a piece of meta-data
                    def name = third
                    data.eachWithIndex() { value, i -> metadata[i][name] = value }
                } else {
                    def languageFamily = first
                    def language = second
                    def source = third
                    data.eachWithIndex() { value, i ->
                        if(value) {
                            records[i]['meta'] = metadata[i]
                            records[i]['data'] = [
                                'semanticField':getSemanticField(metadata[i]['English'], fileName),
                                'category':getCategory(metadata[i]['English']),
                                'languageFamily':languageFamily,
                                'language':language,
                                'source':source,
                                'value':value
                            ]
                        }
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
                        record['data'].each {field -> "$field.key"(field.value) }
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
}

