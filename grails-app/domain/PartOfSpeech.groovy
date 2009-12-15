class PartOfSpeech {

    String name

    static constraints = {
        name(blank:false)
    }

    String toString() {
        return name
    }
}
