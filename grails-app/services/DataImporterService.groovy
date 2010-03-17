import groovy.util.XmlParser

/**
 * This class imports data from an XML file.
 * @author Andrew Young <andrew at vaelen.org>
 */
class DataImporterService {

    boolean transactional = true

    def sessionFactory

    String getValueFromXMLElement(element) {
        def value = element.value.flatten()[0]
        if(value) {
            return value
        } else {
            return ''
        }
    }

    def importXML() {
        def files = []
        new File("xml").eachFileMatch(~/.*\.xml/) { file -> files << file }
        importXML(files)
    }

    def importXML(files) {
        files.each { file ->
            importXML(file)
        }
    }

    def importXML(File input) {
        input.withInputStream() { inputStream ->
            importXML(inputStream)
        }
    }

    def importXML(InputStream input) {

        def outputBuffer = new StringWriter()
        def output = new PrintWriter(outputBuffer)

        def logOutput = { line ->
            // println line
            // output.flush()
            outputBuffer.println line
            outputBuffer.flush()
        }

        def features = new XmlParser().parse(input)
        def type = features.'@type'
        switch (type) {
            case 'lexical':
                importLexicalXML(features, logOutput)
                break
            default:
                break
        }

        return ['output':outputBuffer.toString()]
    }

    def importLexicalXML(features, logOutput) {

        def languageCache = [:]
        def sourceCache = [:]

        def pos = PartOfSpeech.findByName("Unknown")
        logOutput("Part Of Speech: ${pos}")

        def caseStudyRegion = CaseStudyRegion.findByName("All")
        logOutput("Case Study Region: ${caseStudyRegion}")

        features.feature.each() { feature ->
            def meta = [:]
            feature.meta[0].field.each { f ->
//                println "Name: ${f.'@name'}, Value: ${f.value}"
                meta[f.'@name'] = getValueFromXMLElement(f)
            }

            def lexicalFeature = createLexicalFeature(meta, pos, caseStudyRegion, logOutput);

            if(lexicalFeature) {

                feature.data.each { d ->

                    def data = [:]
                    d.field.each { f ->
                       // println "Name: ${f.'@name'}, Value: ${f.value}"
                       data[f.'@name'] = getValueFromXMLElement(f)
                    }

                    def language = createSourceLanguage(data['languageFamily'], data['language'], caseStudyRegion, languageCache, logOutput)

                    if(language) {
                        
                        def lexicalData = LexicalData.findByLexicalFeatureAndSourceLanguage(lexicalFeature, language)

                        if(!lexicalData) {
                            lexicalData = new LexicalData(
        //                        'lexicalFeature': lexicalFeature,
        //                        'sourceLanguage': language,
                                'originalForm': '',
                                'phonemicizedForm': '',
                                'etymologyNotes': '',
                                'semanticNotes': '',
                                'phonologyNotes': '',
                                'grammaticalNotes': '',
                                'generalNotes': ''
                            )
                            
                            if(data['originalForm']) {
                                lexicalData.originalForm = data['originalForm']
                            } else {
                                lexicalData.originalForm = data['phonemicizedForm']
                            }
                            
                            if(data['phonemicizedForm']) {
                                lexicalData.phonemicizedForm = data['phonemicizedForm']
                            } else {
                                lexicalData.phonemicizedForm = data['originalForm']
                            }
                            
                            if (lexicalData.originalForm){
                                for (item in lexicalData.originalForm.tokenize(';')){
                                    
                                    if (item.size() > 255){
                                        logOutput("Error: Original Form: ${item} is too big to save in the database field for originalForm")
                                    }
                                    else{
                                        lexicalData.originalForm = item
                                        lexicalData.lexicalFeature = lexicalFeature
                                        lexicalData.sourceLanguage = language

                                        if (data['source']){
                                            // create sources
                                            for(s in data['source'].tokenize(';')) {
                                                def source = createSource(s, language, sourceCache, logOutput)
                                                if(source) {
                                                    lexicalData.addToSources(source)
                                                }
                                            }
                                        }

                                        if(!lexicalData.save(flush:true)) {
                                            lexicalData.errors.each { error ->
                                                logOutput("Error Saving Lexical Data: ${error}")
                                            }
                                            lexicalData = null
                                        } else {
                                            logOutput("Created Lexical Data: ${lexicalData}")
                                        }
                                    }
                                } // end for(item in lexicalData...)
                            }
                        }
                    }
                }
            }
        }

        def lexicalDataList = LexicalData.list()

        logOutput("Lexical Feature Count: ${LexicalFeature.list().size()}")
        logOutput("Lexical Data Count: ${lexicalDataList.size()}")

    }

    def createSource(title, sourceLanguage, sourceCache, logOutput) {
        def source = null
        if(title) {
            source = sourceCache[title]
            if(!source) {
                source = Source.findByTitle(title)
                if(source) {
                    sourceCache[title] = source
                }
            }
            if(!source) {
                source = new Source(
                    'editor':'UNKNOWN',
                    'author':'UNKNOWN',
                    'title':title,
                    'placeOfPublication':'UNKNOWN',
                    'dateOfPublication':'UNKNOWN',
                    'publisher':'UNKNOWN',
                    'publisherDetails':'',
                    'sourceLanguage':sourceLanguage
                )
                if(!source.save()) {
                    source.errors.each { error ->
                        logOutput("Error Saving Source: ${error}")
                    }
                    source = null
                } else {
                    logOutput("Created Source: ${source}")
                }
            }
        }
        return source
    }

    def createSourceLanguage(languageFamilyName, languageName, caseStudyRegion, languageCache, logOutput) {
        //println "createSourceLanguage(languageFamilyName: ${languageFamilyName}, languageName: ${languageName}, caseStudyRegion: ${caseStudyRegion})"
        if(!languageFamilyName) {
            languageFamilyName = 'UNKNOWN'
        }
        if(!languageName) {
            languageName = 'UNKNOWN'
        }
        def cacheKey = "${languageFamilyName}||${languageName}"
        def language = languageCache[cacheKey]
        if(!language) {
            language = SourceLanguage.findByFamilyAndName(languageFamilyName, languageName)
            if(language) {
                languageCache[cacheKey] = language
            }
        }
        if(!language) {
            language = new SourceLanguage(
                'family': languageFamilyName,
                'name': languageName,
//                'caseStudyRegion': caseStudyRegion,
                'isoCode': 'xxx',
                'otherNames': '',
                'subGroup': '',
                'longitude': 0,
                'latitude': 0,
                'notes': ''
            )
            language.caseStudyRegion = caseStudyRegion
            if(!language.save()) {
                language.errors.each { error ->
                    logOutput("Error Saving Source Language: ${error}")
                }
                language = null
            } else {
                logOutput("Created Source Language: ${language}")
                languageCache[cacheKey] = language
            }
        }
        return language
    }

    /**
     * Create a given LexicalFeature object if it doesn't already exist.
     * Otherwise return it.
     */
    def createLexicalFeature(meta, pos, caseStudyRegion, logOutput) {
        def lexicalFeature = null
        if(meta['englishHeadword']) {
            lexicalFeature = LexicalFeature.findByEnglishHeadword(meta['englishHeadword'])
            if(!lexicalFeature) {
                def semanticField = createSemanticField(meta['semanticField'], logOutput)
                def category = createLexicalFeatureCategory(meta['category'], logOutput)
                lexicalFeature = new LexicalFeature(
                    'englishHeadword':meta['englishHeadword'],
                    'portugueseHeadword': '',
                    'spanishHeadword': '',
                    'latinHeadword': '',
                    'frenchHeadword': '',
                    'partOfSpeech':pos,
                    'caseStudyRegion': caseStudyRegion,
                    'comments': ''
                )
                if(meta['portugueseHeadword']) {
                    lexicalFeature.portugueseHeadword = meta['portugueseHeadword']
                }
                if(meta['spanishHeadword']) {
                    lexicalFeature.spanishHeadword = meta['spanishHeadword']
                }
                if(meta['latinHeadword']) {
                    lexicalFeature.latinHeadword = meta['latinHeadword']
                }
                if(meta['comments']) {
                    lexicalFeature.comments = meta['comments']
                }
                if(semanticField) {
                    lexicalFeature.semanticField = semanticField
                }
                if(category) {
                    lexicalFeature.category = category
                }
                if(!lexicalFeature.save()) {
                    lexicalFeature.errors.each { error ->
                        logOutput("Error Saving Lexical Feature: ${error}")
                    }
                    lexicalFeature = null
                } else {
                    logOutput("Created Lexical Feature: ${lexicalFeature}")
                }
            }
        }
        return lexicalFeature
    }

    /**
     * Create a given LexicalFeatureCategory object if it doesn't already exist.
     * Otherwise return it.
     */
    def createLexicalFeatureCategory(name, logOutput) {
        def category = LexicalFeatureCategory.findByName(name)
        if(!category) {
            category = new LexicalFeatureCategory('name':name)
            if(!category.save()) {
                category.errors.each { error ->
                    logOutput("Error Saving Lexical Feature Category: ${error}")
                }
                category = null
            } else {
                logOutput("Created Lexical Feature Category: ${category}")
            }
        }
        return category
    }

     /**
     * Create a given SemanticField object if it doesn't already exist.
     * Otherwise return it.
     */
    def createSemanticField(name, logOutput) {
        def semanticField = SemanticField.findByName(name)
        if(!semanticField) {
            semanticField = new SemanticField('name':name)
            if(!semanticField.save()) {
                semanticField.errors.each { error ->
                    logOutput("Error Saving Semantic Field: ${error}")
                }
                semanticField = null
            } else {
                logOutput("Created Semantic Field: ${semanticField}")
            }
        }
        return semanticField
    }
}

