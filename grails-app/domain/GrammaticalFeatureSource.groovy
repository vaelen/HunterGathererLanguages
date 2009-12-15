class GrammaticalFeatureSource {

    GrammaticalFeature grammaticalFeature
    Source source

    static constraints = {
        grammaticalFeature(blank:false)
        source(blank:false)
    }
}
