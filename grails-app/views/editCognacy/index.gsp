<html>
    <head>
        <title>Do Cognacy</title>
        <meta name="layout" content="main" />
    </head>
    <body>
        <div class="body">
            <h1>Cognacy Judgements.</h1>
            
            <p>Please select a Lexical Feature to do cognacy for.</p>
            
            <g:if test="${flash.message}">
                <div class="message">
                    <g:message code="${flash.message}" args="${flash.args}" default="${flash.defaultMessage}" />
                </div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>

                   	    <g:sortableColumn property="id" title="Id" titleKey="lexicalFeature.id" />

                   	    <g:sortableColumn property="englishHeadword" title="English Headword" titleKey="lexicalFeature.englishHeadword" />

                   	    <g:sortableColumn property="spanishHeadword" title="Spanish Headword" titleKey="lexicalFeature.spanishHeadword" />

                   	    <g:sortableColumn property="portugueseHeadword" title="Portuguese Headword" titleKey="lexicalFeature.portugueseHeadword" />

                   	    <g:sortableColumn property="frenchHeadword" title="French Headword" titleKey="lexicalFeature.frenchHeadword" />

                   	    <g:sortableColumn property="latinHeadword" title="Latin Headword" titleKey="lexicalFeature.latinHeadword" />

                   	    <g:sortableColumn property="semanticField" title="Semantic Field" titleKey="lexicalFeature.semanticField" />

                   	    <g:sortableColumn property="partOfSpeech" title="Part Of Speech" titleKey="lexicalFeature.partOfSpeech" />

                   	    <g:sortableColumn property="category" title="Category" titleKey="lexicalFeature.category" />

                   	    <g:sortableColumn property="caseStudyRegion" title="Case Study Region" titleKey="lexicalFeature.caseStudyRegion" />

                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${LexicalFeatures}" status="i" var="lexicalFeatureInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">


                            <td><g:link action="edit" id="${lexicalFeatureInstance.id}">${fieldValue(bean: lexicalFeatureInstance, field: "id")}</g:link></td>

                            <td>${fieldValue(bean: lexicalFeatureInstance, field: "englishHeadword")}</td>

                            <td>${fieldValue(bean: lexicalFeatureInstance, field: "spanishHeadword")}</td>

                            <td>${fieldValue(bean: lexicalFeatureInstance, field: "portugueseHeadword")}</td>

                            <td>${fieldValue(bean: lexicalFeatureInstance, field: "frenchHeadword")}</td>

                            <td>${fieldValue(bean: lexicalFeatureInstance, field: "latinHeadword")}</td>

                            <td>${fieldValue(bean: lexicalFeatureInstance, field: "semanticField")}</td>

                            <td>${fieldValue(bean: lexicalFeatureInstance, field: "partOfSpeech")}</td>

                            <td>${fieldValue(bean: lexicalFeatureInstance, field: "category")}</td>

                            <td>${fieldValue(bean: lexicalFeatureInstance, field: "caseStudyRegion")}</td>

                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            
            <div class="paginateButtons">
                <g:paginate total="${lexicalFeatureInstanceTotal}" />
            </div>
            
            
            <g:if test="${filterList}">
                <g:render template="/templates/filters" />
            </g:if>
            
        
        </div>
    </body>
</html>
