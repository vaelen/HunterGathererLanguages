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
        englishHeadword(blank:false)
        spanishHeadword()
        portugueseHeadword()
        frenchHeadword()
        semanticField(blank:false, )
        partOfSpeech(blank:false, )
        category(blank:false, )
        caseStudyRegion(blank:false, )
        exportSet()
    }

    String toString() {
        return englishHeadword
    }
}
