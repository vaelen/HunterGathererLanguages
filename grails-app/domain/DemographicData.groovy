import intient.nimble.domain.UserBase

class DemographicData {

    static hasMany = [demographicDataSources: DemographicDataSource]

    DemographicFeature demographicFeature
    SourceLanguage sourceLanguage
    String answer
    String generalNotes
    Integer phylogeneticCode
    Date createdAt
    Date updatedAt
    UserBase createdBy
    UserBase updatedBy

    static constraints = {
        demographicFeature(blank:false, )
        sourceLanguage(blank:false, )
        answer(blank:false)
        generalNotes(widget:"textarea")
        phylogeneticCode(range:1..9)
        demographicDataSources()
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
