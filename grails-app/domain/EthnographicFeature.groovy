class EthnographicFeature {

    String name
    EthnographicFeatureCategory category
    String hrafCategory
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
