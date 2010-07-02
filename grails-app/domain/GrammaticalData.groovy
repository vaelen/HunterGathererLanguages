class GrammaticalData extends LanguageProperty {

    static hasMany = [sources:Source]

    GrammaticalFeature grammaticalFeature
    SourceLanguage sourceLanguage
    String answer
    String originalForm
    String phonemicizedForm
    String etymologyNotes
    String phonologyNotes
    String grammaticalNotes
    String generalNotes
    Integer phylogeneticCode

    static constraints = {
        grammaticalFeature(blank:false)
        sourceLanguage(blank:false)
        answer(blank:false)
        originalForm(blank:true, nullable:true)
        phonemicizedForm(blank:true, nullable:true)
        sources()
        phylogeneticCode(range:1..9, blank:true, nullable:true)
        etymologyNotes(widget:"textarea", maxSize:2000)
        phonologyNotes(widget:"textarea", maxSize:2000)
        grammaticalNotes(widget:"textarea", maxSize:2000)
        generalNotes(widget:"textarea", maxSize:2000)
        addDateConstraints()
    }
}
