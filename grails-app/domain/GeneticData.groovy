class GeneticData extends LanguageProperty {

    static hasMany = [sources:Source]

    GeneticFeature geneticFeature
    SourceLanguage sourceLanguage
    String answer
    String generalNotes
    Integer phylogeneticCode

    static constraints = {
        geneticFeature(blank:false)
        sourceLanguage(blank:false)
        answer(blank:false)
        sources()
        phylogeneticCode(range:1..9, blank:true, nullable:true)
        generalNotes(widget:"textarea", maxSize:2000)
        createdAt(display: false, nullable: true)
        updatedAt(display: false, nullable: true)
        createdBy(display: false, nullable: true)
        updatedBy(display: false, nullable: true)
    }
}
