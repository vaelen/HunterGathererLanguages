<html>
    <head>
        <title>Do Cognacy</title>
        <meta name="layout" content="main" />
    </head>
    <body>
        <div class="body">
            <h1>Cognacy Judgements.</h1>
            
            <g:if test="${flash.message}">
                <div class="message">
                    <g:message code="${flash.message}" args="${flash.args}" default="${flash.defaultMessage}" />
                </div>
            </g:if>
            
            <div class="list">
                <table>
                    <thead>
                        <tr>
                           <g:sortableColumn property="id" title="Id" titleKey="lexicalData.id" />
                           <g:sortableColumn property="sourceLanguage" title="Source Language" titleKey="lexicalData.sourceLanguage" />
                           <g:sortableColumn property="originalForm" title="Original Form" titleKey="lexicalData.originalForm" />
                           <g:sortableColumn property="phonemicizedForm" title="Phonemicized Form" titleKey="lexicalData.phonemicizedForm" />
                           <g:sortableColumn property="glossInSource" title="Gloss In Source" titleKey="lexicalData.glossInSource" />
                           <g:sortableColumn property="loanSource" title="Loan Source" titleKey="lexicalData.loanSource" />
                           <g:sortableColumn property="inheritanceLevel" title="Inheritance Level" titleKey="lexicalData.inheritanceLevel" />
                           <g:sortableColumn property="phylogeneticCode" title="Phylogenetic Code" titleKey="lexicalData.phylogeneticCode" />
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="${entries}" status="i" var="lexicalDataInstance">
                        <tr class="${(i % 2) == 0 ? 'odd' : 'even'}">
                            <td>${fieldValue(bean: lexicalDataInstance, field: "id")}</td>
                            <td>${fieldValue(bean: lexicalDataInstance, field: "sourceLanguage")}</td>
                            <td>${fieldValue(bean: lexicalDataInstance, field: "originalForm")}</td>
                            <td>${fieldValue(bean: lexicalDataInstance, field: "phylogeneticCode")}</td>
                            <td>${fieldValue(bean: lexicalDataInstance, field: "glossInSource")}</td>
                            <td>${fieldValue(bean: lexicalDataInstance, field: "loanSource")}</td>
                            <td>${fieldValue(bean: lexicalDataInstance, field: "inheritanceLevel")}</td>
                            <td>${fieldValue(bean: lexicalDataInstance, field: "phylogeneticCode")}</td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            
        </div>
    </body>
</html>
