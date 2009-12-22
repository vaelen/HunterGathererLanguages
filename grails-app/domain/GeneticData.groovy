import intient.nimble.domain.UserBase

class GeneticData {

    static hasMany = [geneticDataSources: GeneticDataSource]

    GeneticFeature geneticFeature
    SourceLanguage sourceLanguage
    String answer
    String generalNotes
    Integer phylogeneticCode
    Date createdAt
    Date updatedAt
    UserBase createdBy
    UserBase updatedBy

    static constraints = {
        geneticFeature(blank:false)
        sourceLanguage(blank:false)
        answer(blank:false)
        geneticDataSources()
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
