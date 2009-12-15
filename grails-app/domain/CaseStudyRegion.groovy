import org.cubika.labs.scaffolding.annotation.FlexScaffoldProperty

@FlexScaffoldProperty(labelField="name")

class CaseStudyRegion {

    String name

    static constraints = {
        name(blank:false)
    }

    String toString() {
        return name
    }
}
