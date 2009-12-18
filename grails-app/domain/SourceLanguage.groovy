class SourceLanguage {

    String name
    String otherNames
    String subGroup
    String family
    CaseStudyRegion caseStudyRegion
    String notes
    String latitude
    String longitude
    String isoCode

    static constraints = {
        name(blank:false)
        otherNames(blank:true)
        subGroup(blank:true)
        family(blank:false)
        caseStudyRegion(blank:false, )
        notes(widget:"textarea")
        latitude(blank:true)
        longitude(blank:true)
        isoCode(blank:false, minSize:3, maxSize:3)
    }

    String toString() {
        return name
    }
}
