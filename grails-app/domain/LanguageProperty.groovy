// Abstract out common features of all language-related domain classes.
abstract class LanguageProperty {
    Date createdAt
    Date updatedAt
    User createdBy
    User updatedBy

    // If we specify `constraints' directly, these will end up as
    // the first columns in table views.  We usually want them last,
    // or sometimes in the middle.  To implement this, we put the
    // constraints in a function and call the function from the
    // `constraints' list in each class.
    static addDateConstraints = {
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
