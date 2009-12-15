class GeneticData {

    static hasMany = [geneticSources: GeneticSource]

    GeneticFeature geneticFeature
    Language language
    String answer
    String generalNotes
    Integer phylogeneticCode

    static constraints = {
    }
}
