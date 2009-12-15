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
        lexicalFeature(blank:false)
        language(blank:false)
        lexicalDataSources(inPlace:true)
        sourcePages()
        originalForm(blank:false)
        glossInSource()
        reconstructions(inPlace:true)
        loanSource(inPlace:true)
        inheritanceLevel(inPlace:true)
        etymologyNotes(widget:"textarea")
        phonologyNotes(widget:"textarea")
        grammaticalNotes(widget:"textarea")
        semanticNotes(widget:"textarea")
        generalNotes(widget:"textarea")
        phylogeneticCode(range:1..9)
    }
}
