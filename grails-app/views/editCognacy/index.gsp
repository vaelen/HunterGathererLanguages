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
              <div id="searchFilters">
                <h1 class="trigger">Filter Results:</h1>
                <div>
                  <g:form name="filterForm" url="[action:'filter']">
                    <g:each in="${filterList}" var="f">
                      <span class="filterfield">
                          <g:set var="fieldName" value="${f.key}" />
                          <g:set var="fieldDef" value="${f.value}" />
                          <g:set var="currentValue" value="${filters[fieldName]}" />
                          <label for="${fieldName}">
                            <g:if test="${fieldDef.displayName}">
                              ${fieldDef.displayName}:&nbsp;
                            </g:if>
                            <g:else>
                              ${fieldName}:&nbsp;
                            </g:else>
                          </label>
                          <g:if test="${fieldDef.type}">
                            <g:if test="${fieldDef.type == 'textField'}">
                              <g:if test="${fieldDef.value}">
                                <g:textField name="${fieldName}" class="filterinput" value="${currentValue ? currentValue : fieldDef.value}" />
                              </g:if>
                              <g:else>
                                <g:textField name="${fieldName}" class="filterinput" value="${currentValue ? currentValue : ''}" />
                              </g:else>
                            </g:if>
                            <g:if test="${fieldDef.type == 'hiddenField'}">
                              <g:if test="${fieldDef.value}">
                                <g:hiddenField name="${fieldName}" class="filterinput" value="${fieldDef.value}" />
                              </g:if>
                              <g:else>
                                <g:hiddenField name="${fieldName}" />
                              </g:else>
                            </g:if>
                            <g:if test="${fieldDef.type == 'checkBox'}">
                              <g:if test="${fieldDef.value}">
                                <g:checkBox name="${fieldName}" class="filterinput" value="${fieldDef.value}" checked="${currentValue}"/>
                              </g:if>
                              <g:else>
                                <g:checkBox name="${fieldName}" class="filterinput" checked="${currentValue}"/>
                              </g:else>
                            </g:if>
                            <g:if test="${fieldDef.type == 'radio'}">
                              <g:each in="${fieldDef.values}" var="o">
                                <g:radio name="${fieldName}" class="filterinput" value="${fieldDef.value}" checked="${o.value == currentValue}"/>
                              </g:each>
                            </g:if>
                            <g:if test="${fieldDef.type == 'select'}">
                              <g:select name="${fieldName}"
                                        from="${fieldDef.values}"
                                        class="filterinput" 
                                        value="${currentValue}"
                                        optionKey="${fieldDef.optionKey ? fieldDef.optionKey : 'id'}"
                                        optionValue="${fieldDef.optionValue ? fieldDef.optionValue : ''}"
                                        noSelection="['':'']" />
                            </g:if>
                          </g:if>
                          <g:else>
                            <g:if test="${fieldDef.value}">
                              <g:textField name="${fieldName}" class="filterinput" value="${currentValue ? currentValue : fieldDef.value}" />
                            </g:if>
                            <g:else>
                              <g:textField name="${fieldName}" class="filterinput" value="${currentValue ? currentValue : ''}" />
                            </g:else>
                          </g:else>
                      </span>
                    </g:each>
                    <span class="filterfield">
                        <g:submitButton class="filterinput" name="_submit" value="Set Filter" /><br/>
                    </span>
                  </g:form>
                </div>
              </div>
            </g:if>
            
        
        </div>
    </body>
</html>
