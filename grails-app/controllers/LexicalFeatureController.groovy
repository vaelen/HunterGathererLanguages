import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.codehaus.groovy.grails.web.util.WebUtils

class LexicalFeatureController extends FeatureControllerBase {
    // Everything below here added by Andrew Young

    // Returns a list of things you can filter by;
    // see code docs in main class Controller
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
