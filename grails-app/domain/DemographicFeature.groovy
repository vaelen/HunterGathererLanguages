class DemographicFeature {

    static hasMany = [demographicFeatureSources: DemographicFeatureSource]

    String name
    DemographicFeatureCategory category
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
        demographicFeatureSources()
        notes(widget:"textarea")
    }

    String toString() {
        return name
    }
}
