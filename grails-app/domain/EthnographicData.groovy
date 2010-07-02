class EthnographicData extends LanguageProperty {

    static hasMany = [sources:Source]

    EthnographicFeature ethnographicFeature
    SourceLanguage sourceLanguage
    String answer
    String originalForm
    String phonemicizedForm
    String generalNotes
    Integer phylogeneticCode

    static constraints = {
        ethnographicFeature(blank:false)
        sourceLanguage(blank:false)
        answer(blank:false)
        originalForm(blank:false)
        phonemicizedForm(blank:false)
        sources()
        phylogeneticCode(range:1..9, blank:true, nullable:true)
        generalNotes(widget:"textarea", maxSize:2000)
        addDateConstraints()
    }
}
