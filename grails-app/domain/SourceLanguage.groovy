class SourceLanguage extends NamedLanguageProperty {

    String otherNames
    String subGroup
    String family
    CaseStudyRegion caseStudyRegion
    String notes
    String latitude
    String longitude
    String isoCode

    static constraints = {
        otherNames(blank:true)
        subGroup(blank:true)
        family(blank:false)
        caseStudyRegion(blank:false)
        latitude(blank:true)
        longitude(blank:true)
        isoCode(blank:false, minSize:3, maxSize:3)
        notes(widget:"textarea", maxSize:2000)
        // For bizarre reasons, replacing the following with a call to
        // addDateConstraints() leads to a class-load error when trying
        // to run `grails run-app'; but it looks like we can't do the
        // substitution anyway.  See comment above addDateConstraints().
        createdAt(display: false, nullable: true)
        updatedAt(display: false, nullable: true)
        createdBy(display: false, nullable: true)
        updatedBy(display: false, nullable: true)
    }

    String toString() {
        return "$name ($family)"
    }
}
