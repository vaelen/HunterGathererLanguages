class EthnographicData {

    static hasMany = [ethnographicDataSources: EthnographicDataSource]

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
        ethnographicDataSources()
        generalNotes(widget:"textarea")
        phylogeneticCode(range:1..9)
    }
}
