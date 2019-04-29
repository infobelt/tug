import spock.lang.Ignore
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Stepwise

import static com.infobelt.tug.ServiceSpec.service

@Ignore
@Stepwise
class ServiceHandlerSpecification extends Specification {

    @Shared
            serviceInstance = service {
                urlBase "/api/"
                resource {
                    name "widget"
                }
            }

    @Shared
    def widgetId = 0

    def "Loading a basic specification"() {
        given:
        serviceInstance
        when:
        serviceInstance
        then:
        serviceInstance != null
    }

    def "Create a widget"() {
        given:
        serviceInstance
        when:
        def widget = serviceInstance.resources.widgets.create([widgetName: "Cheese"])
        widgetId = widget.id
        then:
        widget.id != null
        widget.widgetName == "Cheese"
    }

    def "Get a widget"() {
        given:
        serviceInstance
        when:
        def widget = serviceInstance.resources.widgets.get(widgetId)
        then:
        widget != null
        widget.id == widgetId
        widget.widgetName == "Cheese"
    }

    def "List widgets"() {
        given:
        serviceInstance
        when:
        def page = serviceInstance.resources.widgets.list()
        print(page)
        then:
        page != null
    }

}
