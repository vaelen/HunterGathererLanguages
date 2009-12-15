import org.cubika.labs.scaffolding.annotation.FlexScaffoldProperty

@FlexScaffoldProperty(labelField="name")
class GrammaticalFeature {

    static hasMany = [grammaticalFeatureSources: GrammaticalFeatureSource]

    String name
    GrammaticalFeatureCategory category
    Boolean underDiscussion
    ExportSet exportSet
    CaseStudyRegion caseStudyRegion
    String notes
    
    static constraints = {
        name(blank:false)
        category(blank:false, inPlace:true)
        underDiscussion()
        exportSet(inPlace:true)
        caseStudyRegion(blank:false, inPlace:true)
        grammaticalFeatureSources(inPlace:true)
        notes(widget:"textarea")
    }

    String toString() {
        return name
    }
}
