class EthnographicDataSource {

    EthnographicData ethnographicData
    Source source

    static constraints = {
        ethnographicData(blank:false)
        source(blank:false)
    }
}
