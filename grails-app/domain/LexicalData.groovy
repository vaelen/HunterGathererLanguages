class LexicalData extends LanguageProperty {

    static hasMany = [sources:Source, reconstructions:Reconstruction]

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
        lexicalFeature(blank:false, unique:'sourceLanguage')
        sourceLanguage(blank:false)
        sources(blank:true, nullable:true)
        sourcePages(blank:true, nullable:true)
        originalForm(blank:false, maxSize:255)
        phonemicizedForm(blank:false, maxSize:255)
        glossInSource(blank:true, nullable:true)
        reconstructions(blank:true, nullable:true)
        loanSource(blank:true, nullable:true)
        inheritanceLevel(blank:true, nullable:true)
        phylogeneticCode(range:1..9, blank:true, nullable:true)
        etymologyNotes(widget:"textarea", maxSize:2000)
        phonologyNotes(widget:"textarea", maxSize:2000)
        grammaticalNotes(widget:"textarea", maxSize:2000)
        semanticNotes(widget:"textarea", maxSize:2000)
        generalNotes(widget:"textarea", maxSize:2000)
        createdAt(display: false, nullable: true)
        updatedAt(display: false, nullable: true)
        createdBy(display: false, nullable: true)
        updatedBy(display: false, nullable: true)
    }
}
