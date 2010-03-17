import org.apache.shiro.SecurityUtils
import org.codehaus.groovy.grails.web.util.WebUtils

class EditCognacyController {
    
    // Set Allowed Methods 
    static allowedMethods = [
        save:['POST']
    ]
    
    
    def getFilterList = {
        return [
            englishHeadword:[displayName:'English Headword'],
            spanishHeadword:[displayName:'Spanish Headword'],
            portugueseHeadword:[displayName:'Portuguese Headword'],
            frenchHeadword:[displayName:'French Headword'],
            latinHeadword:[displayName:'Latin Headword'],
            caseStudyRegion:[
                displayName:'Case Study Region',
                type:'select',
                values:CaseStudyRegion.list()
            ],
            semanticField:[
                displayName:'Semantic Field',
                type:'select',
                values:SemanticField.list()
            ],
            category:[
                displayName:'Category',
                type:'select',
                values:LexicalFeatureCategory.list()
            ],
            partOfSpeech:[
                displayName:'Part of Speech',
                type:'select',
                values:PartOfSpeech.list()
            ],
            exportSet:[
                displayName:'Export Set',
                type:'select',
                values:ExportSet.list()
            ],

        ]
    }

    def doFilter = { params, filters ->
        def c = LexicalFeature.createCriteria()
        def results = c.list(params) {
            if(filters['englishHeadword']) {
                ilike('englishHeadword', filters['englishHeadword'].replaceAll(/\*/, '%'))
            }
            if(filters['spanishHeadword']) {
                ilike('spanishHeadword', filters['spanishHeadword'].replaceAll(/\*/, '%'))
            }
            if(filters['portugueseHeadword']) {
                ilike('portugueseHeadword', filters['portugueseHeadword'].replaceAll(/\*/, '%'))
            }
            if(filters['frenchHeadword']) {
                ilike('frenchHeadword', filters['frenchHeadword'].replaceAll(/\*/, '%'))
            }
            if(filters['latinHeadword']) {
                ilike('latinHeadword', filters['latinHeadword'].replaceAll(/\*/, '%'))
            }
            if(filters['semanticField']) {
                eq('semanticField.id', filters['semanticField'].toLong())
            }
            if(filters['caseStudyRegion']) {
                eq('caseStudyRegion.id', filters['caseStudyRegion'].toLong())
            }
            if(filters['category']) {
                eq('category.id', filters['category'].toLong())
            }
            if(filters['partOfSpeech']) {
                eq('partOfSpeech.id', filters['partOfSpeech'].toLong())
            }
            if(filters['exportSet']) {
                eq('exportSet.id', filters['exportSet'].toLong())
            }
        }
        return results
    }
    
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

    // This method takes a filter string and produces a list of params
    def parseFilter = { filterString ->
        def filters = [:]
        if(filterString) {
            filterString.split("&").each { pair ->
                def parts = pair.split("=")
                def key = parts[0].decodeURL()
                if(parts.length > 1) {
                    def value = parts[1].decodeURL()
                    filters[key] = value
                }
            }
        }
        return filters
    }
    
    def index = {
        def filterList = getFilterList()
        def filters = parseFilter(params.filter)
        
        def values = doFilter(params, filters)
        
        def size = values.size()
        return [ LexicalFeatures: values, size: size, filterList: filterList, filter: params.filter, filters:filters ]
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
    
