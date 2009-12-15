class GeneticFeatureSource {

    GeneticFeature geneticFeature
    Source source

    static constraints = {
        geneticFeature(blank:false)
        source(blank:false)
    }
}
