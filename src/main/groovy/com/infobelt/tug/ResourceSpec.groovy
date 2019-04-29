package com.infobelt.tug

import groovy.util.logging.Slf4j
import groovyx.net.http.ContentType
import groovyx.net.http.HttpResponseException
import org.atteo.evo.inflector.English

@Slf4j
class ResourceSpec {

    String name
    String pluralName
    ServiceHandler handler
    ContentType contentType = ContentType.JSON

    void name(String name) { this.name = name }

    void pluralName(String pluralName) { this.pluralName = pluralName }

    def get(id) {
        try {
            def path = "/" + endpoint() + "/" + id
            log.info("Performing GET on ${endpoint()} with ID ${id} [${path}]")
            def response = handler.client().get(path: path, contentType: contentType)
            return response.responseData
        } catch (HttpResponseException e) {
            log.error("Error making request (HTTP code ${e.statusCode})")
            throw e
        }
    }

    def endpoint() {
        pluralName != null ? pluralName : English.plural(name)
    }

    def delete(id) {
        try {
            def path = "/" + endpoint() + "/" + id
            log.info("Performing GET on ${endpoint()} with ID ${id} [${path}]")
            def response = handler.client().delete(path: path)
            return response.responseData
        } catch (HttpResponseException e) {
            log.error("Error making request (HTTP code ${e.statusCode})")
            throw e
        }
    }

    def list() {
        try {
            def path = "/" + endpoint()
            log.info("Performing GET on ${endpoint()} [${path}]")
            def response = handler.client().get(path: path)
            return response.responseData
        } catch (HttpResponseException e) {
            log.error("Error making request (HTTP code ${e.statusCode})")
            throw e
        }
    }

    def create(Map instance) {
        try {
            log.info("Performing POST on ${endpoint()} with object ${instance}")
            def response = handler.client().post(path: endpoint(), body: instance, requestContentType: contentType)
            log.info("POST response ${response.responseData}")

            return response.responseData
        } catch (HttpResponseException e) {
            log.error("Error making request (HTTP code ${e.statusCode})")
            throw e
        }
    }

    def update(instance) {
        try {
            def response = handler.client().put(path: endpoint(), body: instance, requestContentType: contentType)
            return response.responseData
        } catch (HttpResponseException e) {
            log.error("Error making request (HTTP code ${e.statusCode})")
            throw e
        }
    }
}
