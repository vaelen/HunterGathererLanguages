<% import org.codehaus.groovy.grails.orm.hibernate.support.ClosureEventTriggeringInterceptor as Events %>
<%=packageName%>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
        <meta name="layout" content="main" />
        <title><g:message code="${domainClass.propertyName}.list" default="${domainClass.naturalName} List" /></title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="\${createLinkTo(dir: '')}"><g:message code="home" default="Home" /></a></span>
            <span class="menuButton"><g:link class="create" action="create"><g:message code="${domainClass.propertyName}.new" default="New ${domainClass.naturalName}" /></g:link></span>
        </div>
        <div class="body">
            <h1><g:message code="${domainClass.propertyName}.list" default="${domainClass.naturalName} List" /></h1>
            <g:if test="\${flash.message}">
            <div class="message"><g:message code="\${flash.message}" args="\${flash.args}" default="\${flash.defaultMessage}" /></div>
            </g:if>
            <div class="list">
                <table>
                    <thead>
                        <tr>
                        <%  excludedProps = ["version",
                                             Events.ONLOAD_EVENT,
                                             Events.BEFORE_INSERT_EVENT,
                                             Events.BEFORE_UPDATE_EVENT,
                                             Events.BEFORE_DELETE_EVENT]
                            props = domainClass.properties.findAll { !excludedProps.contains(it.name) && it.type != Set.class }
                            Collections.sort(props, comparator.constructors[0].newInstance([domainClass] as Object[]))
                            props.eachWithIndex { p, i ->
                   	        if (i < 10) {
                                            cp = domainClass.constrainedProperties[p.name]
                   	            if (p.oneToMany || p.manyToMany) { %>
                   	    <th><g:message code="${domainClass.propertyName}.${p.name}" default="${p.naturalName}" /></th>
                   	    <%      } else if (cp && ("textarea" == cp.widget || cp.maxSize > 250 || cp.password)) {
                                                // Do Nothing
                   	            } else { %>
                   	    <g:sortableColumn property="${p.name}" title="${p.naturalName}" titleKey="${domainClass.propertyName}.${p.name}" />
                        <%  }   }   } %>
                        </tr>
                    </thead>
                    <tbody>
                    <g:each in="\${${propertyName}List}" status="i" var="${propertyName}">
                        <tr class="\${(i % 2) == 0 ? 'odd' : 'even'}">
                        <%  props.eachWithIndex { p, i ->
                                cp = domainClass.constrainedProperties[p.name]
                                if (i == 0) { %>

                            <td><g:link action="show" id="\${${propertyName}.id}">\${fieldValue(bean: ${propertyName}, field: "${p.name}")}</g:link></td>
                        <%      } else if (i < 10) {
                                    if (p.type == Boolean.class || p.type == boolean.class) { %>
                            <td><g:formatBoolean boolean="\${${propertyName}.${p.name}}" /></td>
                        <%          } else if (p.type == Date.class || p.type == java.sql.Date.class || p.type == java.sql.Time.class || p.type == Calendar.class) { %>
                            <td><g:formatDate date="\${${propertyName}.${p.name}}" /></td>
                        <%          } else if (BigDecimal.class.isAssignableFrom(p.type)) { %>
                            <td><g:formatNumber number="\${${propertyName}.${p.name}}" /></td>
          	        <%          } else if (cp && ("textarea" == cp.widget || cp.maxSize > 250 || cp.password)) {
                                                // Do Nothing
                                    } else { %>
                            <td>\${fieldValue(bean: ${propertyName}, field: "${p.name}")}</td>
                        <%  }   }   } %>
                        </tr>
                    </g:each>
                    </tbody>
                </table>
            </div>
            <div class="paginateButtons">
                <g:paginate total="\${${propertyName}Total}" />
            </div>
            <export:formats />
	<!-- Added by Andrew Young to implement filtering. -->
            <g:if test="\${filterList}">
              <div id="searchFilters">
                <h1 class="trigger">Filters:</h1>
                <div>
                  <g:form name="filterForm" url="[action:'filter']">
                    <g:each in="\${filterList}" var="f">
                      <g:set var="fieldName" value="\${f.key}" />
                      <g:set var="fieldDef" value="\${f.value}" />
                      <g:set var="currentValue" value="\${filters[fieldName]}" />
                      <label for="\${fieldName}">
                        <g:if test="\${fieldDef.displayName}">
                          \${fieldDef.displayName}:&nbsp;
                        </g:if>
                        <g:else>
                          \${fieldName}:&nbsp;
                        </g:else>
                      </label>
                      <g:if test="\${fieldDef.type}">
                        <g:if test="\${fieldDef.type == 'textField'}">
                          <g:if test="\${fieldDef.value}">
                            <g:textField name="\${fieldName}" value="\${currentValue ? currentValue : fieldDef.value}" />
                          </g:if>
                          <g:else>
                            <g:textField name="\${fieldName}" value="\${currentValue ? currentValue : ''}" />
                          </g:else>
                        </g:if>
                        <g:if test="\${fieldDef.type == 'hiddenField'}">
                          <g:if test="\${fieldDef.value}">
                            <g:hiddenField name="\${fieldName}" value="\${fieldDef.value}" />
                          </g:if>
                          <g:else>
                            <g:hiddenField name="\${fieldName}" />
                          </g:else>
                        </g:if>
                        <g:if test="\${fieldDef.type == 'checkBox'}">
                          <g:if test="\${fieldDef.value}">
                            <g:checkBox name="\${fieldName}" value="\${fieldDef.value}" checked="\${currentValue}"/>
                          </g:if>
                          <g:else>
                            <g:checkBox name="\${fieldName}" checked="\${currentValue}"/>
                          </g:else>
                        </g:if>
                        <g:if test="\${fieldDef.type == 'radio'}">
                          <g:each in="\${fieldDef.values}" var="o">
                            <g:radio name="\${fieldName}" value="\${fieldDef.value}" checked="\${o.value == currentValue}"/>
                          </g:each>
                        </g:if>
                        <g:if test="\${fieldDef.type == 'select'}">
                          <g:select name="\${fieldName}"
                                    from="\${fieldDef.values}"
                                    value="\${currentValue}"
                                    optionKey="\${fieldDef.optionKey ? fieldDef.optionKey : 'id'}"
                                    optionValue="\${fieldDef.optionValue ? fieldDef.optionValue : ''}"
                                    noSelection="['':'']" />
                        </g:if>
                      </g:if>
                      <g:else>
                        <g:if test="\${fieldDef.value}">
                          <g:textField name="\${fieldName}" value="\${currentValue ? currentValue : fieldDef.value}" />
                        </g:if>
                        <g:else>
                          <g:textField name="\${fieldName}" value="\${currentValue ? currentValue : ''}" />
                        </g:else>
                      </g:else>
                      <br/>
                    </g:each>
                    <g:submitButton name="_submit" value="Filter" /><br/>
                  </g:form>
                </div>
              </div>
            </g:if>
	<!-- End code added by Andrew Young to implement filtering. -->
        </div>
    </body>
</html>
