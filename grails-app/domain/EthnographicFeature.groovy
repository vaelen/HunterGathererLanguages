class EthnographicFeature extends NamedLanguageProperty {

    static hasMany = [sources:Source]

    EthnographicFeatureCategory category
    String hrafCategory
    Boolean underDiscussion
    ExportSet exportSet
    CaseStudyRegion caseStudyRegion
    String notes

    static constraints = {
        category(blank:false, )
        hrafCategory(blank:true)
        underDiscussion()
        exportSet()
        caseStudyRegion(blank:false, )
        sources()
        notes(widget:"textarea", maxSize:2000)
        createdAt(display: false, nullable: true)
        updatedAt(display: false, nullable: true)
        createdBy(display: false, nullable: true)
        updatedBy(display: false, nullable: true)
    }
}
