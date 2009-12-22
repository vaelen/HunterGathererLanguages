class LexicalData {

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
    Date createdAt
    Date updatedAt
    User createdBy
    User updatedBy

    String standardizedGloss() {
        return lexicalFeature.englishHeadword
    }

    static constraints = {
        lexicalFeature(blank:false)
        sourceLanguage(blank:false)
        sources()
        sourcePages()
        originalForm(blank:false)
        phonemicizedForm(blank:false)
        glossInSource()
        reconstructions()
        loanSource()
        inheritanceLevel()
        phylogeneticCode(range:1..9)
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
    
    def beforeInsert = {
        createdAt = new Date()
        updatedAt = new Date()
    createdBy = updatedBy
    }

    def beforeUpdate = {
        updatedAt = new Date()
    }


}
