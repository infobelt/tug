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
            resources.put(r.pluralName, r)
            r.handler = this
        })
    };

    def client(path = "") {
        def url = "http://" + serviceSpec.hostname + ":" + serviceSpec.port + "/" + serviceSpec.urlBase + "/" + path
        log.info("Built client [${url}]")
        def restClient = new RESTClient(url)

        if (serviceSpec.auth) {
            log.info("Applying auth")
            serviceSpec.auth.apply(restClient)
        }

        restClient
    }

}
