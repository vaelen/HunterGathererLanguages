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
