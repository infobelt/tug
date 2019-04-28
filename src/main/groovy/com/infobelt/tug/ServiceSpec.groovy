package com.infobelt.tug

import groovy.util.logging.Slf4j

@Slf4j
class ServiceSpec {

    String hostname = "localhost"

    int port = 8080

    String urlBase = "/api"

    List<ResourceSpec> resources = []

    void hostnme(String hostname) { this.hostname = hostname }

    void port(int port) { this.port = port }

    void urlBase(String urlBase) { this.urlBase = urlBase }

    void resource(@DelegatesTo(strategy = Closure.DELEGATE_ONLY, value = ResourceSpec) Closure cl) {
        def resourceSpec = new ResourceSpec()
        def code = cl.rehydrate(resourceSpec, this, this)
        code.resolveStrategy = Closure.DELEGATE_ONLY
        code()
        resources << resourceSpec
    }

    def static service(@DelegatesTo(strategy = Closure.DELEGATE_ONLY, value = ServiceSpec) Closure cl) {
        def serviceSpec = new ServiceSpec()
        def code = cl.rehydrate(serviceSpec, this, this)
        code.resolveStrategy = Closure.DELEGATE_ONLY
        code()
        new ServiceHandler(serviceSpec)
    }

}
