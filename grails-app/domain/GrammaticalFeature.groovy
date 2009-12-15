class GrammaticalFeature {

    String name
    GrammaticalFeatureCategory category
    Boolean underDiscussion
    ExportSet exportSet
    CaseStudyRegion caseStudyRegion
    String notes
    
    static constraints = {
    }

    String toString() {
        return name
    }
}
