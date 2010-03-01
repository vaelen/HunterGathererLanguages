class ImportController {

    def dataImporterService

    def index = {}

    def importLexicalXML = {
        dataImporterService.importXML()
    }

}
