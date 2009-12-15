class GeneticDataSource {

    GeneticData geneticData
    Source source

    static constraints = {
        geneticData(blank:false)
        source(blank:false)
    }
}
