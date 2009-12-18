class DemographicData {

    static hasMany = [demographicDataSources: DemographicDataSource]

    DemographicFeature demographicFeature
    SourceLanguage sourceLanguage
    String answer
    String generalNotes
    Integer phylogeneticCode

    static constraints = {
        demographicFeature(blank:false, )
        sourceLanguage(blank:false, )
        answer(blank:false)
        generalNotes(widget:"textarea")
        phylogeneticCode(range:1..9)
        demographicDataSources()
    }
}
