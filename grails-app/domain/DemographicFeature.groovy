class DemographicFeature {

    String name
    DemographicFeatureCategory category
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
