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

            <g:if test="${error_list}">
                <div class="error">
                    <ul>
                    <g:each in="${error_list}" var="err">
                        <li>${err}</li>
                    </g:each>
                    </ul>
                </div>
            </g:if>
            
            <g:form action="save" method="post" useToken="true">
            <g:hiddenField name="lexicalFeature" value="${feature.id}" />
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
                            <td>${fieldValue(bean: lexicalDataInstance, field: "phonemicizedForm")}</td>
                            <td>${fieldValue(bean: lexicalDataInstance, field: "glossInSource")}</td>
                            <td>${fieldValue(bean: lexicalDataInstance, field: "loanSource")}</td>
                            <td>${fieldValue(bean: lexicalDataInstance, field: "inheritanceLevel")}</td>
                            <td class="c${lexicalDataInstance.phylogeneticCode.encodeAsHTML()}">
                                 <g:textField name="cognate.${lexicalDataInstance.id}" 
                                        value="${fieldValue(bean: lexicalDataInstance, field: 'phylogeneticCode')}" 
                                        size="5" />
                            </td>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
                
                 <div class="buttons">
                     <span class="button">
                         <g:submitButton name="save" class="save" value="${message(code: 'save', 'default': 'Save Cognates')}" />
                     </span>
                 </div>
              </div>
            </g:form>
        </div>
    </body>
</html>
