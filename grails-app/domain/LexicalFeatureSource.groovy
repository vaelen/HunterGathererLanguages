class LexicalFeatureSource {

    LexicalFeature lexicalFeature
    Source source

    static constraints = {
        lexicalFeature(blank:false)
        source(blank:false)
    }
}
