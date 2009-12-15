import org.cubika.labs.scaffolding.annotation.FlexScaffoldProperty

@FlexScaffoldProperty(labelField="englishHeadword")
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
        semanticField(blank:false, inPlace:true)
        partOfSpeech(blank:false, inPlace:true)
        category(blank:false, inPlace:true)
        caseStudyRegion(blank:false, inPlace:true)
        exportSet(inPlace:true)
    }

    String toString() {
        return englishHeadword
    }
}
