

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title><g:message code="lexicalData.list" default="Lexical Data List" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir: '')}"><g:message code="home" default="Home" /></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="lexicalData.new" default="New Lexical Data" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="lexicalData.list" default="Lexical Data List" /></h1>
            <g:if test="${flash.message}">
            <div class="message"><g:message code="${flash.message}" args="${flash.args}" default="${flash.defaultMessage}" /></div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        
                   	    <g:sortableColumn property="id" title="Id" titleKey="lexicalData.id" />
                        
                   	    <g:sortableColumn property="lexicalFeature" title="Lexical Feature" titleKey="lexicalData.lexicalFeature" />
                        
                   	    <g:sortableColumn property="sourceLanguage" title="Source Language" titleKey="lexicalData.sourceLanguage" />
                        
                   	    <g:sortableColumn property="sourcePages" title="Source Pages" titleKey="lexicalData.sourcePages" />
                        
                   	    <g:sortableColumn property="glossInSource" title="Gloss In Source" titleKey="lexicalData.glossInSource" />
                        
                   	    <g:sortableColumn property="loanSource" title="Loan Source" titleKey="lexicalData.loanSource" />
                        
                   	    <g:sortableColumn property="inheritanceLevel" title="Inheritance Level" titleKey="lexicalData.inheritanceLevel" />
                        
                   	    <g:sortableColumn property="phylogeneticCode" title="Phylogenetic Code" titleKey="lexicalData.phylogeneticCode" />
                        
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${lexicalDataInstanceList}" status="i" var="lexicalDataInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                        

                            <td><g:link action="show" id="${lexicalDataInstance.id}">${fieldValue(bean: lexicalDataInstance, field: "id")}</g:link></td>
                        
                            <td>${fieldValue(bean: lexicalDataInstance, field: "lexicalFeature")}</td>
                        
                            <td>${fieldValue(bean: lexicalDataInstance, field: "sourceLanguage")}</td>
                        
                            <td>${fieldValue(bean: lexicalDataInstance, field: "sourcePages")}</td>
                        
                            <td>${fieldValue(bean: lexicalDataInstance, field: "glossInSource")}</td>
                        
                            <td>${fieldValue(bean: lexicalDataInstance, field: "loanSource")}</td>
                        
                            <td>${fieldValue(bean: lexicalDataInstance, field: "inheritanceLevel")}</td>
                        
                            <td class="<hg:colorise value='${lexicalDataInstance.phylogeneticCode}' />">
                                ${fieldValue(bean: lexicalDataInstance, field: "phylogeneticCode")}
                            </td>
                        
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="${lexicalDataInstanceTotal}" />
            </div>
            
            <export:formats params="${params}" />
            
            <g:if test="${filterList}">
                <g:render template="/templates/filters" />
            </g:if>

        </div>
    </body>
</html>
