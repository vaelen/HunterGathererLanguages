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
        createdAt(display: false, nullable: true)
        updatedAt(display: false, nullable: true)
        createdBy(display: false, nullable: true)
        updatedBy(display: false, nullable: true)
    }

    String toString() {
        return englishHeadword
    }
}
