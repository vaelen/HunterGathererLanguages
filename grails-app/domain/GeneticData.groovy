class GeneticData {

    static hasMany = [geneticDataSources: GeneticDataSource]

    GeneticFeature geneticFeature
    SourceLanguage sourceLanguage
    String answer
    String generalNotes
    Integer phylogeneticCode

    static constraints = {
        geneticFeature(blank:false)
        sourceLanguage(blank:false)
        answer(blank:false)
        geneticDataSources()
        generalNotes(widget:"textarea")
        phylogeneticCode(range:1..9)
    }
}
