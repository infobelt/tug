package com.infobelt.tug

import groovy.util.logging.Slf4j
import groovyx.net.http.RESTClient

@Slf4j
class ServiceHandler {

    Map<String, ResourceSpec> resources = [:]

    ServiceSpec serviceSpec

    ServiceHandler(ServiceSpec serviceSpec) {
        this.serviceSpec = serviceSpec

        serviceSpec.resources.forEach({ r ->
            resources.put(r.endpoint(), r)
            r.handler = this
        })
    }

    def client() {
        def url = "http://" + serviceSpec.hostname + ":" + serviceSpec.port + "/" + serviceSpec.urlBase
        log.info("Built client [${url}]")
        return new RESTClient(url)
    }

}
