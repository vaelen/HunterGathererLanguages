class GrammaticalData {

    static hasMany = [grammaticalDataSources: GrammaticalDataSource]

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
        originalForm(blank:false)
        phonemicizedForm(blank:false)
        grammaticalDataSources()
        etymologyNotes(widget:"textarea")
        phonologyNotes(widget:"textarea")
        grammaticalNotes(widget:"textarea")
        generalNotes(widget:"textarea")
        phylogeneticCode(range:1..9)
    }
}
