class LexicalFeature {

    String englishHeadword
    String spanishHeadword
    String portugueseHeadword
    String frenchHeadword
    SemanticField semanticField
    PartOfSpeech partOfSpeech
    CaseStudyRegion caseStudyRegion
    ExportSet exportSet
    LexicalFeatureCategory category

    static constraints = {
    }

    String toString() {
        return name
    }
}
