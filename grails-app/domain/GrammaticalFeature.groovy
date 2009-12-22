import intient.nimble.domain.UserBase

class GrammaticalFeature {

    static hasMany = [grammaticalFeatureSources: GrammaticalFeatureSource]

    String name
    GrammaticalFeatureCategory category
    Boolean underDiscussion
    ExportSet exportSet
    CaseStudyRegion caseStudyRegion
    String notes
    Date createdAt
    Date updatedAt
    UserBase createdBy
    UserBase updatedBy
    
    static constraints = {
        name(blank:false)
        category(blank:false, )
        underDiscussion()
        exportSet()
        caseStudyRegion(blank:false, )
        grammaticalFeatureSources()
        notes(widget:"textarea")
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
