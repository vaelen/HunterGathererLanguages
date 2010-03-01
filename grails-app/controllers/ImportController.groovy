class ImportController {

    def dataImporterService

    def index = {}

    def importXML = {
        InputStream importFile = request.getFile("file").inputStream
        dataImporterService.importXML(importFile)
    }

}
