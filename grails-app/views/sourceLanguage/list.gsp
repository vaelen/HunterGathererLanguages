

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title><g:message code="sourceLanguage.list" default="Source Language List" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir: '')}"><g:message code="home" default="Home" /></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="sourceLanguage.new" default="New Source Language" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="sourceLanguage.list" default="Source Language List" /></h1>
            <g:if test="${flash.message}">
            <div class="message"><g:message code="${flash.message}" args="${flash.args}" default="${flash.defaultMessage}" /></div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                   	    <g:sortableColumn property="id" title="Id" titleKey="sourceLanguage.id" />
                        
                   	    <g:sortableColumn property="name" title="Name" titleKey="sourceLanguage.name" />
                        
                   	    <g:sortableColumn property="otherNames" title="Other Names" titleKey="sourceLanguage.otherNames" />
                        
                   	    <g:sortableColumn property="subGroup" title="Sub Group" titleKey="sourceLanguage.subGroup" />
                        
                   	    <g:sortableColumn property="family" title="Family" titleKey="sourceLanguage.family" />
                        
                   	    <g:sortableColumn property="caseStudyRegion" title="Case Study Region" titleKey="sourceLanguage.caseStudyRegion" />
                        
                   	    <g:sortableColumn property="latitude" title="Latitude" titleKey="sourceLanguage.latitude" />
                        
                   	    <g:sortableColumn property="longitude" title="Longitude" titleKey="sourceLanguage.longitude" />
                        
                   	    <g:sortableColumn property="isoCode" title="Iso Code" titleKey="sourceLanguage.isoCode" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${sourceLanguageInstanceList}" status="i" var="sourceLanguageInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        

                            <td><g:link action="show" id="${sourceLanguageInstance.id}">${fieldValue(bean: sourceLanguageInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: sourceLanguageInstance, field: "name")}</td>
                        
                            <td>${fieldValue(bean: sourceLanguageInstance, field: "otherNames")}</td>
                        
                            <td>${fieldValue(bean: sourceLanguageInstance, field: "subGroup")}</td>
                        
                            <td>${fieldValue(bean: sourceLanguageInstance, field: "family")}</td>
                        
                            <td>${fieldValue(bean: sourceLanguageInstance, field: "caseStudyRegion")}</td>
                        
                            <td>${fieldValue(bean: sourceLanguageInstance, field: "latitude")}</td>
                        
                            <td>${fieldValue(bean: sourceLanguageInstance, field: "longitude")}</td>
                        
                            <td>${fieldValue(bean: sourceLanguageInstance, field: "isoCode")}</td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${sourceLanguageInstanceTotal}" />
            </div>
            
            <export:formats params="${params}" />
            
            <g:if test="${filterList}">
                <g:render template="/templates/filters" />
            </g:if>


        </div>
    </body>
</html>
