// Base class of "scaffolded" controllers.  These controllers have the code
// in src/templates/scaffolding/Controller.groovy textually inserted into
// them.
//
// Why don't we scaffold all classes?  Well, if you scaffold
// EditCognacyController.groovy, you get a weird error
//
// 2010-07-02 14:11:21,714 [main] ERROR plugins.DefaultGrailsPlugin  - Cannot generate
// controller logic for scaffolded class true. It is not a domain class!
//
// This appears to be due to the fact that a `def index = { ... }' exists both in
// Controller.groovy and EditCognacyController.groovy.  Getting rid of the scaffolding
// makes this error go away.
class ScaffoldedControllerBase extends ControllerBase {

    // This has to be done on each controller or it doesn't bind it to anything.
    def exportService

    // This causes the actual textual inserting to happen.
    def scaffold = true
}
