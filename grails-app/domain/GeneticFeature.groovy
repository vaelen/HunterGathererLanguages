class GeneticFeature {

    static hasMany = [geneticFeatureSources:GeneticFeatureSource]

    String name
    GeneticFeatureCategory category
    Boolean underDiscussion
    ExportSet exportSet
    CaseStudyRegion caseStudyRegion
    String notes

    static constraints = {
        name(blank:false)
        category(blank:false, )
        underDiscussion()
        exportSet()
        caseStudyRegion(blank:false, )
        geneticFeatureSources()
        notes(widget:"textarea")
    }

    String toString() {
        return name
    }
}
