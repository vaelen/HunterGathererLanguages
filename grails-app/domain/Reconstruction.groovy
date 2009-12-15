class Reconstruction {

    ReconstructionData reconstructionData
    LexicalData lexicalData
    String relationship

    static constraints = {
        reconstructionData(blank:false)
        lexicalData(blank:false)
        relationship(blank:false)
    }
}
