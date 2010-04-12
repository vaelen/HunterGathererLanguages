import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.codehaus.groovy.grails.web.util.WebUtils

class SourceLanguageController {

    def exportService

    def scaffold = true

    def getFilterList = {
        return [
            subGroup:[displayName:'SubGroup'],
            family:[displayName:'Family'],
            caseStudyRegion:[
                displayName:'Case Study Region',
                type:'select',
                values:CaseStudyRegion.list()
            ],
/*            family:[
                displayName:'Family',
                type:'select',
                values:SemanticField.list()
            ],
*/        ]
    }

    def doFilter = { params, filters ->
        def c = SourceLanguage.createCriteria()
        def results = c.list(params) {
            if(filters['subGroup']) {
                ilike('subGroup', filters['subGroup'].replaceAll(/\*/, '%'))
            }
            if(filters['Family']) {
                ilike('Family', filters['Family'].replaceAll(/\*/, '%'))
            }
            if(filters['caseStudyRegion']) {
                eq('caseStudyRegion.id', filters['caseStudyRegion'].toLong())
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
            redirect (view: list, params: [filter:filterString])
        } else {
            redirect (view: list)
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

    def list = {
        def filterList = getFilterList()
        def filters = parseFilter(params.filter)
        params.max = Math.min(params.max ? params.max.toInteger() : 10,  100)
        // Non-HTML output (e.g. save as csv etc)
        if(params?.format && params.format != "html") {
            params.remove('max') // make download all
            params.remove('offset') // make download all
            response.contentType = ConfigurationHolder.config.grails.mime.types[params.format]
            response.setHeader("Content-disposition", "attachment; filename=languages.${params.extension}")
            exportService.export(params.format, response.outputStream, doFilter(params, filters), [:], [:])
        // Standard HTML output
        } else {
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
            return [
                    sourceLanguageInstanceList: values, 
                    sourceLanguageInstanceTotal: size, 
                    filterList: filterList, 
                    filter: params.filter, 
                    filters:filters
            ]
        }
    }



}
