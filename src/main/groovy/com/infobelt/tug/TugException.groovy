package com.infobelt.tug

import groovy.util.logging.Slf4j
import groovyx.net.http.HttpResponseException

@Slf4j
class TugException extends RuntimeException {

    TugException(HttpResponseException httpResponseException) {
        super("Error making request (HTTP code ${e.statusCode})", e)
        log.error("Error making request (HTTP code ${e.statusCode})")
    }

}
