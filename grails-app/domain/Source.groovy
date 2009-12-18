class Source {

    String author
    String editor
    String title
    String placeOfPublication
    String dateOfPublication
    String publisher
    String publisherDetails
    SourceLanguage sourceLanguage

    static constraints = {
        title(blank:false)
        sourceLanguage(blank:false, )
        dateOfPublication(blank:false)
        author(blank:false)
        editor()
        placeOfPublication()
        publisher()
        publisherDetails()
    }

    String toString() {
        return title
    }
}
