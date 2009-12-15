class EthnographicData {

    static hasMany = [ethnographicDataSources: EthnographicDataSource]

    EthnographicFeature ethnographicFeature
    Language language
    String answer
    String originalForm
    String phonemicizedForm
    String generalNotes
    Integer phylogeneticCode

    static constraints = {
        ethnographicFeature(blank:false)
        language(blank:false)
        answer(blank:false)
        originalForm(blank:false)
        phonemicizedForm(blank:false)
        ethnographicDataSources(inPlace:true)
        generalNotes(widget:"textarea")
        phylogeneticCode(range:1..9)
    }
}
