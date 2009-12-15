class DemographicData {

    static hasMany = [demographicDataSources: DemographicDataSource]

    DemographicFeature demographicFeature
    Language language
    String answer
    String generalNotes
    Integer phylogeneticCode

    static constraints = {
    }
}
