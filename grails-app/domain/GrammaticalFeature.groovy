class GrammaticalFeature {

    static hasMany = [grammaticalFeatureSources: GrammaticalFeatureSource]

    String name
    GrammaticalFeatureCategory category
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
        grammaticalFeatureSources()
        notes(widget:"textarea")
    }

    String toString() {
        return name
    }
}
