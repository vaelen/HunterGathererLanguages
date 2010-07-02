class ScaffoldedControllerBase extends ControllerBase {

    // This has to be done on each controller or it doesn't bind it to anything.
    def exportService

    def scaffold = true
}
