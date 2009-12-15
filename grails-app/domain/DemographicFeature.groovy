import org.cubika.labs.scaffolding.annotation.FlexScaffoldProperty

@FlexScaffoldProperty(labelField="name")
class DemographicFeature {

    static hasMany = [demographicFeatureSources: DemographicFeatureSource]

    String name
    DemographicFeatureCategory category
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
        demographicFeatureSources(inPlace:true)
        notes(widget:"textarea")
    }

    String toString() {
        return name
    }
}
