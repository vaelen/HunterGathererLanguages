import org.cubika.labs.scaffolding.annotation.FlexScaffoldProperty

@FlexScaffoldProperty(labelField="name")
class Language {

    String name
    String otherNames
    String subgroup
    String family
    CaseStudyRegion caseStudyRegion
    String notes
    Float latitude
    Float longitude
    String isoCode

    static constraints = {
        name(blank:false)
        otherNames()
        subGroup()
        family(blank:false)
        caseStudyRegion(blank:false, inPlace:true)
        notes(widget:"textarea")
        latitude()
        longitude()
        isoCode(blank:false, minSize:3, maxSize:3)
    }

    String toString() {
        return name
    }
}
