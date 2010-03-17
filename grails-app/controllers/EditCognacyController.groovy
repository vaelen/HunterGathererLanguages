import org.apache.shiro.SecurityUtils

class EditCognacyController {
    
    def scaffold = LexicalData
    
    // Set Allowed Methods 
    static allowedMethods = [
        saveCognates:['POST']
    ]
    
    def index = {
        [ LexicalFeatures: LexicalFeature.list(params) ]
    }
    
/*    def doCognates = {
        def feature = LexicalFeature.get(params.id)
        
        if (!feature){
            flash.message = "Lexical Feature Not Found with id ${params.id}"
            redirect(action:listCognates)
        }
        else{
            [feature: feature]
        }
        
    }
    
*/    
/*    // Enter Lexicon Page
    def enterLexicon = {
        
        // no .max selected, set to default
        if (!params.max){
            params.max = DefaultRows
        }
        
        // have an entry list, so make sure we show them all
        if (params.entry_list){
            params.max = params.entry_list.size()
        }
        // no entry_list
        else{
            params.entry_list = false
        }
        [max:params.max, entry_list:params.entry_list]
    }
    
*/    // Save lexicon handler
/*    def saveCognacy = {
        def user = User.get(SecurityUtils.getSubject()?.getPrincipal())
        def errors = []
        def saved_count = 0
        for (id in params._entries_) {
            def instance = new LexicalData(params["entry{$id}"])
            instance.updatedBy = user
            instance.validate()
            if (instance.hasErrors()) {
                errors.add(0, instance)
            }
            else{
                instance.save()
                saved_count += 1
            }
        }
        if (errors.size > 0){ // errors - send back with errors.
            flash.message = "${saved_count} Lexical Entries Created, ${errors.size} entries NOT saved due to errors"
            println errors
            redirect(action:enterLexicon, params:["entry_list":errors])
        }
        else{ // all good
            flash.message = "${saved_count} Lexical Entries Created"
            redirect(action:enterLexicon) // go back
        }
    }*/
    
}
    
