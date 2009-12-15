class DemographicFeatureSource {

    DemographicFeature demographicFeature
    Source source

    static constraints = {
        demographicFeature(blank:false)
        source(blank:false)
    }
}
