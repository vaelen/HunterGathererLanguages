class User extends intient.nimble.domain.UserBase {

    // Extend UserBase with your custom values here
   
    Date createdAt
    Date updatedAt

    static constraints = {
        createdAt(display: false, nullable: true)
        updatedAt(display: false, nullable: true)
    }

    def beforeInsert = {
        createdAt = new Date()
        updatedAt = new Date()
    }

    def beforeUpdate = {
        updatedAt = new Date()
    }

    String toString() {
        return "FOOBAR!"
        /*if(profile != null) {
            return profile.fullName
        } else {
            return username
        }*/
    }

}
