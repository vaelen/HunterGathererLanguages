class ReconstructionData {

    static hasMany = [reconstructions: Reconstruction]

    Reconstruction reconstruction
    String protoSourceLanguage
    String form
    String gloss
    String notes

    static constraints = {
        reconstruction(blank:false, )
        protoSourceLanguage(blank:false)
        form(blank:false)
        gloss(blank:false)
        notes(widget:"textarea")
    }
}
