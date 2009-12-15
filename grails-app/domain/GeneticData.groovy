class GeneticData {

    static hasMany = [geneticDataSources: GeneticDataSource]

    GeneticFeature geneticFeature
    Language language
    String answer
    String generalNotes
    Integer phylogeneticCode

    static constraints = {
        geneticFeature(blank:false)
        language(blank:false)
        answer(blank:false)
        geneticDataSources()
        generalNotes(widget:"textarea")
        phylogeneticCode(range:1..9)
    }
}
