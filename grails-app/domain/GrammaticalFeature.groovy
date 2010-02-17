class GrammaticalFeature {

    static hasMany = [sources:Source]

    String name
    GrammaticalFeatureCategory category
    Boolean underDiscussion
    ExportSet exportSet
    CaseStudyRegion caseStudyRegion
    String notes
    Date createdAt
    Date updatedAt
    User createdBy
    User updatedBy
    
    static constraints = {
        name(blank:false)
        category(blank:false)
        underDiscussion(default:true)
        exportSet()
        caseStudyRegion(blank:false, )
        sources()
        notes(widget:"textarea", maxSize:2000)
        createdAt(display: false, nullable: true)
        updatedAt(display: false, nullable: true)
        createdBy(display: false, nullable: true)
        updatedBy(display: false, nullable: true)
    }
    
    def beforeInsert = {
        createdAt = new Date()
        updatedAt = new Date()
    createdBy = updatedBy
    }

    def beforeUpdate = {
        updatedAt = new Date()
    }


    String toString() {
        return name
    }
}
