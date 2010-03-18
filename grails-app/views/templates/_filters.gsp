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