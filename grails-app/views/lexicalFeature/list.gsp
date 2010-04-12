<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title><g:message code="lexicalFeature.list" default="Lexical Feature List" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir: '')}"><g:message code="home" default="Home" /></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="lexicalFeature.new" default="New Lexical Feature" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="lexicalFeature.list" default="Lexical Feature List" /></h1>
            <g:if test="${flash.message}">
            <div class="message"><g:message code="${flash.message}" args="${flash.args}" default="${flash.defaultMessage}" /></div>
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
                    <g:each in="${lexicalFeatureInstanceList}" status="i" var="lexicalFeatureInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        

                            <td><g:link action="show" id="${lexicalFeatureInstance.id}">${fieldValue(bean: lexicalFeatureInstance, field: "id")}</g:link></td>
                        
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
            <export:formats params="${params}" />
            
            <g:if test="${filterList}">
                <g:render template="/templates/filters" />
            </g:if>
            
        </div>
    </body>
</html>
