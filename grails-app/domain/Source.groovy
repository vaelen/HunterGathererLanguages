class Source {

    String author
    String editor
    String title
    String placeOfPublication
    String dateOfPublication
    String publisher
    String publisherDetails
    Language language

    static constraints = {
        title(blank:false)
        language(blank:false, )
        dateOfPublication(blank:false)
        author()
        editor()
        placeOfPublication()
        publisher()
        publisherDetails()
    }

    String toString() {
        return title
    }
}
