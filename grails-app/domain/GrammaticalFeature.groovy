class GrammaticalFeature extends NamedLanguageProperty {

    static hasMany = [sources:Source]

    GrammaticalFeatureCategory category
    Boolean underDiscussion
    ExportSet exportSet
    CaseStudyRegion caseStudyRegion
    String notes

    static constraints = {
        category(blank:false)
        underDiscussion(default:true)
        exportSet()
        caseStudyRegion(blank:false, )
        sources()
        notes(widget:"textarea", maxSize:2000)
        addDateConstraints()
    }
}
