import intient.nimble.domain.UserBase

class DemographicFeatureSource {

    DemographicFeature demographicFeature
    Source source
    Date createdAt
    Date updatedAt
    UserBase createdBy
    UserBase updatedBy

    static constraints = {
        demographicFeature(blank:false)
        source(blank:false)
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


}
