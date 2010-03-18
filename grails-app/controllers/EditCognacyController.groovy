import org.apache.shiro.SecurityUtils
import org.codehaus.groovy.grails.web.util.WebUtils

class EditCognacyController {
    
    // Set Allowed Methods 
    static allowedMethods = [
        save:['POST']
    ]
    
    def getFilterList = LexicalFeatureController.getFilterList
    def doFilter = LexicalFeatureController.doFilter
    def filter = LexicalFeatureController.filter
    def parseFilter = LexicalFeatureController.parseFilter
    
    def index = {
        
        params.max = Math.min(params.max ? params.max.toInteger() : 10,  100)
        
        def filterList = getFilterList()
        def filters = parseFilter(params.filter)
        
        def values = doFilter(params, filters)

        def size = 0
        if (values.metaClass.hasProperty(values, 'totalCount')) {
            size = values.totalCount
        } else if (values.metaClass.respondsTo(values, 'totalCount')) {
            size = values.totalCount()
        } else if (values.metaClass.hasProperty(values, 'count')) {
            size = values.count
        } else if (values.metaClass.respondsTo(values, 'count')) {
            size = values.count()
        } else if (values.metaClass.hasProperty(values, 'size')) {
            size = values.size
        } else if (values.metaClass.respondsTo(values, 'size')) {
            size = values.size()
        }
        
        return [ LexicalFeatures: values, lexicalFeatureInstanceTotal: size, filterList: filterList, filter: params.filter, filters:filters ]
    }
    
    def edit = {
        def feature = LexicalFeature.get(params.id)
        if (!feature){
            flash.message = "Lexical Feature Not Found with id ${params.id}"
            redirect(action:index)
        }
        else{
            [feature: feature]
        }
    }
        
    /*
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
    
