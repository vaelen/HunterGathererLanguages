class GrammaticalData {

    static hasMany = [grammaticalSources: GrammaticalSource]

    GrammaticalFeature grammaticalFeature
    Language language
    String answer
    String originalForm
    String phonemicizedForm
    String etymologyNotes
    String phonologyNotes
    String grammaticalNotes
    String generalNotes
    Integer phylogeneticCode

    static constraints = {
    }
}
