class LexicalData {

    static hasMany = [lexicalDataSources: LexicalDataSource, reconstructions: Reconstruction]

    LexicalFeature lexicalFeature
    SourceLanguage sourceLanguage
    String sourcePages
    String originalForm
    String phonemicizedForm
    String glossInSource
    SourceLanguage loanSource
    SourceLanguage inheritanceLevel
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
        lexicalFeature(blank:false)
        sourceLanguage(blank:false)
        lexicalDataSources()
        sourcePages()
        originalForm(blank:false)
        glossInSource()
        reconstructions()
        loanSource()
        inheritanceLevel()
        etymologyNotes(widget:"textarea")
        phonologyNotes(widget:"textarea")
        grammaticalNotes(widget:"textarea")
        semanticNotes(widget:"textarea")
        generalNotes(widget:"textarea")
        phylogeneticCode(range:1..9)
    }
}
