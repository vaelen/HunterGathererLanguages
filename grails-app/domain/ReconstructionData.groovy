class ReconstructionData {

    static hasMany = [reconstructions:Reconstruction]

    String protoSourceLanguage
    String form
    String gloss
    String notes
    Date createdAt
    Date updatedAt
    User createdBy
    User updatedBy

    static constraints = {
        protoSourceLanguage(blank:false)
        form(blank:false)
        gloss(blank:false)
        reconstructions()
        notes(widget:"textarea", maxSize:2000)
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
