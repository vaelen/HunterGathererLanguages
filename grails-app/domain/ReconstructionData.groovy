class ReconstructionData {

    static hasMany = [reconstructions: Reconstruction]

    Reconstruction reconstruction
    String protoLanguage
    String form
    String gloss
    String notes

    static constraints = {
        reconstruction(blank:false, )
        protoLanguage(blank:false)
        form(blank:false)
        gloss(blank:false)
        notes(widget:"textarea")
    }
}
