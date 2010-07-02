class DemographicData extends LanguageProperty {

    static hasMany = [sources:Source]

    DemographicFeature demographicFeature
    SourceLanguage sourceLanguage
    String answer
    String generalNotes
    Integer phylogeneticCode

    static constraints = {
        demographicFeature(blank:false, )
        sourceLanguage(blank:false)
        answer(blank:false)
        phylogeneticCode(range:1..9, blank:true, nullable:true)
        sources()
        generalNotes(widget:"textarea", maxSize:2000)
        addDateConstraints()
    }
}
