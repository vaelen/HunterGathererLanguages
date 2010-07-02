class DemographicFeature extends NamedLanguageProperty {

    static hasMany = [sources:Source]

    DemographicFeatureCategory category
    Boolean underDiscussion
    ExportSet exportSet
    CaseStudyRegion caseStudyRegion
    String notes

    static constraints = {
        category(blank:false, )
        underDiscussion()
        exportSet()
        caseStudyRegion(blank:false, )
        sources()
        notes(widget:"textarea", maxSize:2000)
        addDateConstraints()
    }
}
