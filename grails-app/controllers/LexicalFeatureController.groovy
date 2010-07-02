import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.codehaus.groovy.grails.web.util.WebUtils

@Mixin(FilterBehavior)
class LexicalFeatureController extends FeatureControllerBase {
    // Everything below here added by Andrew Young

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

    def list = {
        def filterList = getFilterList()
        def filters = parseFilter(params.filter)
        params.max = Math.min(params.max ? params.max.toInteger() : 10,  100)
        // Non-HTML output (e.g. save as csv etc)
        if(params?.format && params.format != "html") {
            params.remove('max')
            params.remove('offset')
            response.contentType = ConfigurationHolder.config.grails.mime.types[params.format]
            response.setHeader("Content-disposition", "attachment; filename=lexicalfeature-list.${params.extension}")
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
                lexicalFeatureInstanceList: values, 
                lexicalFeatureInstanceTotal: size, 
                filterList: filterList, 
                filter: params.filter,
                filters:filters
            ]
        }
    }
}
