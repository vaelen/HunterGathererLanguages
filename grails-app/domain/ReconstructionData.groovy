class ReconstructionData {

    static hasMany = [reconstructions: Reconstruction]

    Reconstruction reconstruction
    String protoSourceLanguage
    String form
    String gloss
    String notes
    Date createdAt
    Date updatedAt
    User createdBy
    User updatedBy

    static constraints = {
        reconstruction(blank:false, )
        protoSourceLanguage(blank:false)
        form(blank:false)
        gloss(blank:false)
        notes(widget:"textarea")
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
