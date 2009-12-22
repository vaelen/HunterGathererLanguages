class LexicalFeature {

    static hasMany = [sources:Source]

    String englishHeadword
    String spanishHeadword
    String portugueseHeadword
    String frenchHeadword
    SemanticField semanticField
    PartOfSpeech partOfSpeech
    CaseStudyRegion caseStudyRegion
    ExportSet exportSet
    LexicalFeatureCategory category
    Date createdAt
    Date updatedAt
    User createdBy
    User updatedBy

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
        sources()
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
