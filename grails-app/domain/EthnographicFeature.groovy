import org.cubika.labs.scaffolding.annotation.FlexScaffoldProperty

@FlexScaffoldProperty(labelField="name")
class EthnographicFeature {

    static hasMany = [ethnographicFeatureSources: EthnographicFeatureSource]

    String name
    EthnographicFeatureCategory category
    String hrafCategory
    Boolean underDiscussion
    ExportSet exportSet
    CaseStudyRegion caseStudyRegion
    String notes

    static constraints = {
        name(blank:false)
        category(blank:false, inPlace:true)
        hrafCategory(blank:true)
        underDiscussion()
        exportSet(inPlace:true)
        caseStudyRegion(blank:false, inPlace:true)
        ethnographicFeatureSources(inPlace:true)
        notes(widget:"textarea")
    }

    String toString() {
        return name
    }
}
