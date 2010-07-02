import org.apache.shiro.SecurityUtils
import org.codehaus.groovy.grails.web.util.WebUtils

// Why don't we scaffold this class (i.e. inherit from ScaffoldedControllerBase)?
// Well, if you do, you get a weird error
//
// 2010-07-02 14:11:21,714 [main] ERROR plugins.DefaultGrailsPlugin  - Cannot generate
// controller logic for scaffolded class true. It is not a domain class!
//
// This appears to be due to the fact that a `def index = { ... }' exists both in
// Controller.groovy and EditCognacyController.groovy.  Getting rid of the scaffolding
// makes this error go away.

@Mixin(FilterBehavior)
class EditCognacyController extends ControllerBase {
    // Set Allowed Methods 
    static allowedMethods = [
        save:['POST']
    ]
    
    // TODO: REFACTOR Away (already in LexicalFeatureController)
    // This method takes params and concatenates them together into a filter string.
    def filter = {
        // Remove the submit button
        def newParams = [:]
        params.each { key, value ->
            if(value && !(key in(['_submit', 'action', 'controller']))) {
                newParams[key] = value
            }
        }
        def filterString = WebUtils.toQueryString(newParams)
        if(filterString.length() > 1) {
            filterString = filterString[1..(filterString.length()-1)]
            redirect (view: index, params: [filter:filterString])
        } else {
            redirect (view: index)
        }
    }
    
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
            [
                feature: feature, 
                entries: LexicalData.findAllByLexicalFeature(feature, [sort: params.sort, order: params.order])
            ]
        }
    }
    
    
    def save = {
        // validate feature
        def feature = LexicalFeature.get(params.lexicalFeature)
        if (!feature){
            flash.message = "Lexical Feature Not Found with id ${params.lexicalFeature}"
            redirect(action:index)
        }
        
        def errors = []
        def saved_count = 0
        // TODO: Replace with Command object?
        params.cognate.each { lex_id, value ->
            lex_id = lex_id.toInteger()
            def instance = LexicalData.get(lex_id)
            
            if (instance.lexicalFeature != feature){
                errors.add(0, "#${lex_id} does NOT belong to feature ${feature}")
            }
            else if (value.isInteger() == false){
                errors.add(0, "Value ${value} for #${lex_id} is NOT a number")
            }
            else if (value.toInteger() == instance.phylogeneticCode){
                // nothing to do...
            }
            else{
                instance.phylogeneticCode = value.toInteger()
                instance.updatedBy = getUser()
                instance.validate()
                if (instance.hasErrors()) {
                    errors.add(0, "#${lex_id} failed to validate")
                }
                else{
                    instance.save()
                    saved_count += 1
                }
                
            }
        }
        // FIXME!! The errors aren't displayed currently.
        if (errors.size > 0){ // errors - send back with errors.
            flash.message = "${saved_count} Cognates Saved, ${errors.size} NOT saved due to errors"
            redirect(action:edit, id:feature.id, error_list:errors)
        }
        else{ // all good
            flash.message = "${saved_count} Cognates Saved"
            redirect(action:edit, id:feature.id) // go back
        }
    }
}
    
