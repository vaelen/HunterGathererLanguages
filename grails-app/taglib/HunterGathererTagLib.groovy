class HunterGathererTagLib {
    static namespace = 'hg'

    def colorise = { attrs, body ->
        def value = attrs.value?.toInteger() ?: 0
        out << "c${value}"
    }
}
