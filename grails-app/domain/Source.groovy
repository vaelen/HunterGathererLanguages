class Source extends LanguageProperty {

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

    static constraints = {
        title(blank:false, maxSize:255)
        sourceLanguage(blank:false, )
        dateOfPublication(blank:false)
        author(blank:false)
        editor()
        placeOfPublication()
        publisher()
        publisherDetails(widget:"textarea", maxSize:2000)
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

    String toString() {
        return title
    }
}
