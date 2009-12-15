class DemographicData {

    static hasMany = [demographicDataSources: DemographicDataSource]

    DemographicFeature demographicFeature
    Language language
    String answer
    String generalNotes
    Integer phylogeneticCode

    static constraints = {
        demographicFeature(blank:false, )
        language(blank:false, )
        answer(blank:false)
        generalNotes(widget:"textarea")
        phylogeneticCode(range:1..9)
        demographicDataSources()
    }
}
