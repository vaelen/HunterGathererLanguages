import org.codehaus.groovy.grails.commons.ConfigurationHolder
import org.apache.shiro.SecurityUtils

<%=packageName ? "package ${packageName}\n\n" : ""%>class ${className}Controller {

    // This has to be done on each controller or it doesn't bind it to anything.
    //def exportService

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

    def list = {
        params.max = Math.min(params.max ? params.max.toInteger() : 10,  100)
        if(params.format <%= "&&" %> params.format != "html") {
            params.max = null
            params.offset = null
            response.contentType = ConfigurationHolder.config.grails.mime.types[params.format]
            response.setHeader("Content-disposition", "attachment; filename=${className.toLowerCase()}-list.\${params.extension}")
            exportService.export(params.format, response.outputStream, ${className}.list(params), [:], [:])
        }

        [${propertyName}List: ${className}.list(params), ${propertyName}Total: ${className}.count()]
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
            return [${propertyName}: ${propertyName}]
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
