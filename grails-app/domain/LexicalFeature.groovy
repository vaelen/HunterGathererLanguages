class LexicalFeature {

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
    Date createdAt
    Date updatedAt
    User createdBy
    User updatedBy

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
    
    def beforeInsert = {
        createdAt = new Date()
        updatedAt = new Date()
    createdBy = updatedBy
    }

    def beforeUpdate = {
        updatedAt = new Date()
    }


    String toString() {
        return englishHeadword
    }
}
