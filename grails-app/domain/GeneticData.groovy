class GeneticData {

    static hasMany = [geneticDataSources: GeneticDataSource]

    GeneticFeature geneticFeature
    Language language
    String answer
    String generalNotes
    Integer phylogeneticCode

    static constraints = {
    }
}
