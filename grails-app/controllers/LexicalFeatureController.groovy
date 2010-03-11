class LexicalFeatureController {

    def exportService

    def scaffold = true

    //def index = { }

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
}
