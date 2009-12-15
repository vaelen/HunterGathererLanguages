class Source {

    static hasMany = [lexicalSources:LexicalSource]

    String author
    String editor
    String title
    String placeOfPublication
    String dateOfPublication
    String publisher
    String publisherDetails
    Language language

    static constraints = {
    }
}
