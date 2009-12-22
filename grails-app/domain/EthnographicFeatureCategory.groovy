import intient.nimble.domain.UserBase

class EthnographicFeatureCategory {

    String name
    Date createdAt
    Date updatedAt
    UserBase createdBy
    UserBase updatedBy

    static constraints = {
        name(blank:false)
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
