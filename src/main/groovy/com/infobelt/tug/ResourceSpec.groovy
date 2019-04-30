package com.infobelt.tug

import groovy.util.logging.Slf4j
import groovyx.net.http.ContentType
import groovyx.net.http.HttpResponseException

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
            def path = "/" + pluralName + "/" + id
            log.info("Performing GET on ${pluralName} with ID ${id} [${path}]")
            def response = handler.client().get(path: path, contentType: contentType)
            return response.responseData
        } catch (HttpResponseException e) {
            throw new TugException(e)
        }
    }

    def delete(id) {
        try {
            def path = pluralName + "/" + id
            log.info("Performing GET on ${path} with ID ${id}")
            def response = handler.client(path).delete()
            return response.responseData
        } catch (HttpResponseException e) {
            throw new TugException(e)
        }
    }

    def list() {
        try {
            log.info("Performing GET on ${pluralName}")
            def response = handler.client(pluralName).get(contentType: contentType)
            return response.responseData
        } catch (HttpResponseException e) {
            throw new TugException(e)
        }
    }

    def create(Map instance) {
        try {
            log.info("Performing POST on ${pluralName} with object ${instance}")
            def response = handler.client(pluralName).post(body: instance, requestContentType: contentType)
            log.info("POST response ${response.responseData}")
            return response.responseData
        } catch (HttpResponseException e) {
            throw new TugException(e)
        }
    }

    def update(instance) {
        try {
            def path = pluralName + "/" + instance.id
            def response = handler.client(path).put(body: instance, requestContentType: contentType)
            return response.responseData
        } catch (HttpResponseException e) {
            throw new TugException(e)
        }
    }
}
