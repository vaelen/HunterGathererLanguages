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
    }

    String toString() {
        return title
    }
}
