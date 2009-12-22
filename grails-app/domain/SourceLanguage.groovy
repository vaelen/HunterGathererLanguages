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
    Date createdAt
    Date updatedAt
    User createdBy
    User updatedBy

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
        createdAt(display: false, nullable: true)
        updatedAt(display: false, nullable: true)
        createdBy(display: false, nullable: true)
        updatedBy(display: false, nullable: true)
    }
    
    def beforeInsert = {
        createdAt = new Date()
        updatedAt = new Date()
        createdBy = updatedBy
    }

    def beforeUpdate = {
        updatedAt = new Date()
    }


    String toString() {
        return name
    }
}
