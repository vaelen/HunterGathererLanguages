// Abstract out features of language-related domain classes that have a
// `name' field, used to return the displayed name of a class.
abstract class NamedLanguageProperty extends LanguageProperty {
    String name

    static constraints = {
        name(blank:false)
    }

    String toString() {
        return name
    }
}
