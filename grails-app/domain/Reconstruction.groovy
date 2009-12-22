class Reconstruction {

    ReconstructionData reconstructionData
    LexicalData lexicalData
    String relationship
    Date createdAt
    Date updatedAt
    User createdBy
    User updatedBy

    static constraints = {
        reconstructionData(blank:false)
        lexicalData(blank:false)
        relationship(blank:false)
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
