class DemographicData {

    static hasMany = [demographicSources: DemographicSource]

    DemographicFeature demographicFeature
    Language language
    String answer
    String generalNotes
    Integer phylogeneticCode

    static constraints = {
    }
}
