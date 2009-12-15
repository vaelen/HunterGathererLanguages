class EthnographicFeatureSource {

    EthnographicFeature ethnographicFeature
    Source source

    static constraints = {
        ethnographicFeature(blank:false)
        source(blank:false)
    }
}
