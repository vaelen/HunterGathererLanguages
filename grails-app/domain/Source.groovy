class Source {

    static hasMany = [demographicData:DemographicData, demographicFeatures:DemographicFeature, ethnographicData:EthnographicData, ethnographicFeatures:EthnographicFeature, geneticData:GeneticData, geneticFeatures:GeneticFeature, grammaticalData:GrammaticalData, grammaticalFeatures:GrammaticalFeature, lexicalData:LexicalData, lexicalFeatures:LexicalFeature]
    static belongsTo = [DemographicData, DemographicFeature, EthnographicData, EthnographicFeature, GeneticData, GeneticFeature, GrammaticalData, GrammaticalFeature, LexicalData, LexicalFeature]

    String author
    String editor
    String title
    String placeOfPublication
    String dateOfPublication
    String publisher
    String publisherDetails
    SourceLanguage sourceLanguage
    Date createdAt
    Date updatedAt
    User createdBy
    User updatedBy

    static constraints = {
        title(blank:false)
        sourceLanguage(blank:false, )
        dateOfPublication(blank:false)
        author(blank:false)
        editor()
        placeOfPublication()
        publisher()
        publisherDetails()
        createdAt(display: false, nullable: true)
        updatedAt(display: false, nullable: true)
        createdBy(display: false, nullable: true)
        updatedBy(display: false, nullable: true)
        demographicData()
        demographicFeatures()
        ethnographicData()
        ethnographicFeatures()
        geneticData()
        geneticFeatures()
        grammaticalData()
        grammaticalFeatures()
        lexicalData()
        lexicalFeatures()
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
        return title
    }
}
