class ReconstructionData {

    static hasMany = [reconstructions: Reconstruction]

    Reconstruction reconstruction
    String protoLanguage
    String form
    String gloss
    String notes

    static constraints = {
    }
}
