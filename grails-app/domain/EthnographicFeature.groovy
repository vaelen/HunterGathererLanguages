class EthnographicFeature {

    static hasMany = [ethnographicFeatureSources: EthnographicFeatureSource]

    String name
    EthnographicFeatureCategory category
    String hrafCategory
    Boolean underDiscussion
    ExportSet exportSet
    CaseStudyRegion caseStudyRegion
    String notes

    static constraints = {
        name(blank:false)
        category(blank:false, )
        hrafCategory(blank:true)
        underDiscussion()
        exportSet()
        caseStudyRegion(blank:false, )
        ethnographicFeatureSources()
        notes(widget:"textarea")
    }

    String toString() {
        return name
    }
}
