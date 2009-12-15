class EthnographicData {

    static hasMany = [ethnographicDataSources: EthnographicDataSource]

    EthnographicFeature ethnographicFeature
    Language language
    String answer
    String originalForm
    String phonemicizedForm
    String generalNotes
    Integer phylogeneticCode

    static constraints = {
    }
}
