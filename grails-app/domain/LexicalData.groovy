class LexicalData {

    static hasMany = [lexicalDataSources: LexicalDataSource, reconstructions: Reconstruction]

    LexicalFeature lexicalFeature
    Language language
    String sourcePages
    String originalForm
    String phonemicizedForm
    String glossInSource
    Language loanSource
    Language inheritanceLevel
    String etymologyNotes
    String phonologyNotes
    String grammaticalNotes
    String semanticNotes
    String generalNotes
    Integer phylogeneticCode

    String standardizedGloss() {
        return lexicalFeature.englishHeadword
    }

    static constraints = {
    }
}
