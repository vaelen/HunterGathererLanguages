class LexicalFeature extends LanguageProperty {

    static hasMany = [sources:Source]

    String englishHeadword
    String spanishHeadword
    String portugueseHeadword
    String frenchHeadword
    String latinHeadword
    SemanticField semanticField
    PartOfSpeech partOfSpeech
    CaseStudyRegion caseStudyRegion
    ExportSet exportSet
    LexicalFeatureCategory category
    String comments

    static constraints = {
        englishHeadword(blank:false)
        spanishHeadword()
        portugueseHeadword()
        frenchHeadword()
        latinHeadword()
        semanticField(blank:false)
        partOfSpeech(blank:false)
        category(blank:false)
        caseStudyRegion(blank:false)
        exportSet(blank:true, nullable: true)
        sources()
        comments(widget: "textarea", maxSize:2000)
        addDateConstraints()
    }

    String toString() {
        return englishHeadword
    }
}
