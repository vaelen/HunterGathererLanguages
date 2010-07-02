abstract class NamedLanguageProperty extends LanguageProperty {
    String name

    static constraints = {
        name(blank:false)
    }

    String toString() {
        return name
    }
}
