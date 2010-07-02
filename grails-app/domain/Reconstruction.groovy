class Reconstruction extends LanguageProperty {

    //static belongsTo = [LexicalData, ReconstructionData]

    ReconstructionData reconstructionData
    LexicalData lexicalData
    String relationship

    static constraints = {
        reconstructionData(blank:false)
        lexicalData(blank:false)
        relationship(blank:false)
        createdAt(display: false, nullable: true)
        updatedAt(display: false, nullable: true)
        createdBy(display: false, nullable: true)
        updatedBy(display: false, nullable: true)
    }
}
