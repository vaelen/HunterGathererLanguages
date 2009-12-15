import org.cubika.labs.scaffolding.annotation.FlexScaffoldProperty

@FlexScaffoldProperty(labelField="name")
class EthnographicFeatureCategory {

    String name

    static constraints = {
        name(blank:false)
    }

    String toString() {
        return name
    }
}
