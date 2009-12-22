class DemographicData {

    static hasMany = [sources:Source]

    DemographicFeature demographicFeature
    SourceLanguage sourceLanguage
    String answer
    String generalNotes
    Integer phylogeneticCode
    Date createdAt
    Date updatedAt
    User createdBy
    User updatedBy

    static constraints = {
        demographicFeature(blank:false, )
        sourceLanguage(blank:false, )
        answer(blank:false)
        phylogeneticCode(range:1..9)
        sources()
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
