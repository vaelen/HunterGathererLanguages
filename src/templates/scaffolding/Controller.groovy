import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.apache.shiro.SecurityUtils
import org.codehaus.groovy.grails.web.util.WebUtils

<%=packageName ? "package ${packageName}\n\n" : ""%>class ${className}Controller {

    // This has to be done on each controller or it doesn't bind it to anything.
    //def exportService

	def hasDeleteButton = true
	
    def getUser = { 
        def user = User.get(SecurityUtils.getSubject()?.getPrincipal()) 
        if (user != null) {
            user = User.get(user.id) // To get the right sort of object
        }
        return user
    }

    def index = { redirect(action: "list", params: params) }

    // the delete, save and update actions only accept POST requests
    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

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
        def c = ${className}.createCriteria()
        def results = c.list(params) {}
        return results
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

    // This method takes a filter string and produces a list of params
    def parseFilter = { filterString ->
        def filters = [:]
        if(filterString) {
            filterString.split(<%= '"&"' %>).each { pair ->
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

    def list = {
        def filterList = getFilterList()
        def filters = parseFilter(params.filter)
        params.max = Math.min(params.max ? params.max.toInteger() : 10,  100)
        if(params.format <%= "&&" %> params.format != "html") {
            params.remove('max')
            params.remove('offset')
            response.contentType = ConfigurationHolder.config.grails.mime.types[params.format]
            response.setHeader("Content-disposition", "attachment; filename=${className.toLowerCase()}-list.\${params.extension}")
            exportService.export(params.format, response.outputStream, doFilter(params, filters), [:], [:])
        } else {

            def values = doFilter(params, filters)

            def size = 0
            if (values.metaClass.hasProperty(values, 'totalCount')) {
                size = values.totalCount
            } else if (values.metaClass.respondsTo(values, 'totalCount')) {
                size = values.totalCount()
            } else if (values.metaClass.hasProperty(values, 'count')) {
                size = values.count
            } else if (values.metaClass.respondsTo(values, 'count')) {
                size = values.count()
            } else if (values.metaClass.hasProperty(values, 'size')) {
                size = values.size
            } else if (values.metaClass.respondsTo(values, 'size')) {
                size = values.size()
            }

            return [${propertyName}List: values, ${propertyName}Total: size, filterList: filterList, filter: params.filter, filters:filters]
        }
    }

    def create = {
        def ${propertyName} = new ${className}()
        ${propertyName}.properties = params
        ${propertyName}.updatedBy = getUser()
        return [${propertyName}: ${propertyName}]
    }

    def save = {
        def ${propertyName} = new ${className}(params)
        ${propertyName}.updatedBy = getUser()
        if (!${propertyName}.hasErrors() && ${propertyName}.save()) {
            flash.message = "${domainClass.propertyName}.created"
            flash.args = [${propertyName}.id]
            flash.defaultMessage = "${className} \${${propertyName}.id} created"
            redirect(action: "show", id: ${propertyName}.id)
        }
        else {
            render(view: "create", model: [${propertyName}: ${propertyName}])
        }
    }

    def show = {
        def ${propertyName} = ${className}.get(params.id)
        if (!${propertyName}) {
            flash.message = "${domainClass.propertyName}.not.found"
            flash.args = [params.id]
            flash.defaultMessage = "${className} not found with id \${params.id}"
            redirect(action: "list")
        }
        else {
            return [hasDeleteButton: hasDeleteButton, ${propertyName}: ${propertyName}]
        }
    }

    def edit = {
        def ${propertyName} = ${className}.get(params.id)
        if (!${propertyName}) {
            flash.message = "${domainClass.propertyName}.not.found"
            flash.args = [params.id]
            flash.defaultMessage = "${className} not found with id \${params.id}"
            redirect(action: "list")
        }
        else {
            return [${propertyName}: ${propertyName}]
        }
    }

    def update = {
        def ${propertyName} = ${className}.get(params.id)
        if (${propertyName}) {
            if (params.version) {
                def version = params.version.toLong()
                if (${propertyName}.version > version) {
                    <%  def lowerCaseName = grails.util.GrailsNameUtils.getPropertyName(className) %>
                    ${propertyName}.errors.rejectValue("version", "${lowerCaseName}.optimistic.locking.failure", "Another user has updated this ${className} while you were editing")
                    render(view: "edit", model: [${propertyName}: ${propertyName}])
                    return
                }
            }
            ${propertyName}.properties = params
            ${propertyName}.updatedBy = getUser()
            if (!${propertyName}.hasErrors() && ${propertyName}.save()) {
                flash.message = "${domainClass.propertyName}.updated"
                flash.args = [params.id]
                flash.defaultMessage = "${className} \${params.id} updated"
                redirect(action: "show", id: ${propertyName}.id)
            }
            else {
                render(view: "edit", model: [${propertyName}: ${propertyName}])
            }
        }
        else {
            flash.message = "${domainClass.propertyName}.not.found"
            flash.args = [params.id]
            flash.defaultMessage = "${className} not found with id \${params.id}"
            redirect(action: "edit", id: params.id)
        }
    }

    def delete = {
        def ${propertyName} = ${className}.get(params.id)
        if (${propertyName}) {
            try {
                ${propertyName}.delete()
                flash.message = "${domainClass.propertyName}.deleted"
                flash.args = [params.id]
                flash.defaultMessage = "${className} \${params.id} deleted"
                redirect(action: "list")
            }
            catch (org.springframework.dao.DataIntegrityViolationException e) {
                flash.message = "${domainClass.propertyName}.not.deleted"
                flash.args = [params.id]
                flash.defaultMessage = "${className} \${params.id} could not be deleted"
                redirect(action: "show", id: params.id)
            }
        }
        else {
            flash.message = "${domainClass.propertyName}.not.found"
            flash.args = [params.id]
            flash.defaultMessage = "${className} not found with id \${params.id}"
            redirect(action: "list")
        }
    }
}
