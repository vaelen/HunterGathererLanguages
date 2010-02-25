class ImportController {

    def dataImporterService

    def index = {}

    def importLexicalXML = {
        dataImporterService.importLexicalXML(new File('xml/foo.xml'))
    }

}
