class GrammaticalData {

    static hasMany = [grammaticalDataSources: GrammaticalDataSource]

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
        grammaticalDataSources()
        etymologyNotes(widget:"textarea")
        phonologyNotes(widget:"textarea")
        grammaticalNotes(widget:"textarea")
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
