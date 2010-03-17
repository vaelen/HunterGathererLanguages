import org.apache.shiro.SecurityUtils

class EditCognacyController {
    
    def scaffold = LexicalData
    
    // Set Allowed Methods 
    static allowedMethods = [
        save:['POST']
    ]
    
    def index = {
        [ LexicalFeatures: LexicalFeature.list(params) ]
    }
    
/*    def do = {
        def feature = LexicalFeature.get(params.id)
        
        if (!feature){
            flash.message = "Lexical Feature Not Found with id ${params.id}"
            redirect(action:listCognates)
        }
        else{
            [feature: feature]
        }
       
    }
    
    def save = {
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
    }
*/
}
    
