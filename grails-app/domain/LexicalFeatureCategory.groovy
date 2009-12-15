class LexicalFeatureCategory {

    String name

    static constraints = {
        name(blank:false)
    }

    String toString() {
        return name
    }

}
