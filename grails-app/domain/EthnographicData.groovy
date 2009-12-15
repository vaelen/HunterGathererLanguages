class EthnographicData {

    static hasMany = [ethnographicSources: EthnographicSource]

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
