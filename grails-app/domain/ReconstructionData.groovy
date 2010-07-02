class ReconstructionData extends LanguageProperty {

    static hasMany = [reconstructions:Reconstruction]

    String protoSourceLanguage
    String form
    String gloss
    String notes

    static constraints = {
        protoSourceLanguage(blank:false)
        form(blank:false)
        gloss(blank:false)
        reconstructions()
        notes(widget:"textarea", maxSize:2000)
        addDateConstraints()
    }
}
