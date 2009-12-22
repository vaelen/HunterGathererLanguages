import intient.nimble.domain.UserBase

class EthnographicFeature {

    static hasMany = [ethnographicFeatureSources: EthnographicFeatureSource]

    String name
    EthnographicFeatureCategory category
    String hrafCategory
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
        hrafCategory(blank:true)
        underDiscussion()
        exportSet()
        caseStudyRegion(blank:false, )
        ethnographicFeatureSources()
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
