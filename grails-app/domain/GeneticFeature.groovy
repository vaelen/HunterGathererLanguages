import org.cubika.labs.scaffolding.annotation.FlexScaffoldProperty

@FlexScaffoldProperty(labelField="name")
class GeneticFeature {

    static hasMany = [geneticFeatureSources:GeneticFeatureSource]

    String name
    GeneticFeatureCategory category
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
        geneticFeatureSources(inPlace:true)
        notes(widget:"textarea")
    }

    String toString() {
        return name
    }
}
