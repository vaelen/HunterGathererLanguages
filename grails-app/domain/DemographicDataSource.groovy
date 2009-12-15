class DemographicDataSource {

    DemographicData demographicData
    Source source

    static constraints = {
        demographicData(blank:false)
        source(blank:false)
    }
}
