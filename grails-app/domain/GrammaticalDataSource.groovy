class GrammaticalDataSource {

    GrammaticalData grammaticalData
    Source source

    static constraints = {
        grammaticalData(blank:false)
        source(blank:false)
    }
}
