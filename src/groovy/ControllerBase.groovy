class ControllerBase {
    def hasDeleteButton = true
        
    def getUser = { 
        def user = User.get(SecurityUtils.getSubject()?.getPrincipal()) 
        if (user != null) {
            user = User.get(user.id) // To get the right sort of object
        }
        return user
    }

    /**
     * This method should return the list of available filters and their possible values.
     *
     * Expected Output: [
     *     fieldName: [
     *         displayName: 'Field Name',
     *         type: 'textField',
     *         value: 1,
     *         values: [
     *             [toString: 'First Value', id: 1],
     *             [toString: 'Second Value', id: 2],
     *             ...
     *         ]
     *     ]
     * ]
     *
     * Possible values for 'type': textField, checkBox, radio, hiddenField, select
     * 
     * If 'type' is 'select' or 'radio', then 'values' is the list of possible values.
     * Otherwise 'value' is used as the value.
     *
     * If 'type' isn't listed, then it defaults to 'textField'.
     *
     * The options 'optionKey' and 'optionValue' can be used to set options for the 'select' tag if 'type' is 'select'.
     * They default to 'id' and 'toString()' respectively.
     *
     */
    def getFilterList = {
        return [:]
    }

    /**
     * This method should process the given list of selected filters and return a list of objects.
     * The default implementation returns all objects.
     * Input: params, filters:[ key:value, key2:value2, ... ]
     */
    def doFilter = { params, filters ->
        def c = createCriteria()
        def results = c.list(params) {}
        return results
    }
    
    // This method takes a filter string and produces a list of params
    def parseFilter = { filterString ->
        def filters = [:]
        if(filterString) {
            filterString.split("&").each { pair ->
                def parts = pair.split("=")
                def key = parts[0].decodeURL()
                if(parts.length > 1) {
                    def value = parts[1].decodeURL()
                    filters[key] = value
                }
            }
        }
        return filters
    }

    // This method takes params and concatenates them together into a filter string.
    def filter = {
        // Remove the submit button
        def newParams = [:]
        params.each { key, value ->
            if(value && !(key in(['_submit', 'action', 'controller']))) {
                newParams[key] = value
            }
        }
        def filterString = WebUtils.toQueryString(newParams)
        if(filterString.length() > 1) {
            filterString = filterString[1..(filterString.length()-1)]
            redirect (view: list, params: [filter:filterString])
        } else {
            redirect (view: list)
        }
    }

    //def index = { }
}
