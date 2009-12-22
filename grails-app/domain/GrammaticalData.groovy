class GrammaticalData {

    static hasMany = [sources:Source]

    GrammaticalFeature grammaticalFeature
    SourceLanguage sourceLanguage
    String answer
    String originalForm
    String phonemicizedForm
    String etymologyNotes
    String phonologyNotes
    String grammaticalNotes
    String generalNotes
    Integer phylogeneticCode
    Date createdAt
    Date updatedAt
    User createdBy
    User updatedBy

    static constraints = {
        grammaticalFeature(blank:false)
        sourceLanguage(blank:false)
        answer(blank:false)
        originalForm(blank:false)
        phonemicizedForm(blank:false)
        sources()
        phylogeneticCode(range:1..9)
        etymologyNotes(widget:"textarea", maxSize:2000)
        phonologyNotes(widget:"textarea", maxSize:2000)
        grammaticalNotes(widget:"textarea", maxSize:2000)
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
