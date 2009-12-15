class LexicalDataSource {

    LexicalData lexicalData
    Source source

    static constraints = {
        lexicalData(blank:false)
        source(blank:false)
    }
}
