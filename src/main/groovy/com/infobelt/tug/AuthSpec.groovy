package com.infobelt.tug

import groovy.util.logging.Slf4j
import groovyx.net.http.RESTClient
import org.apache.http.HttpRequest
import org.apache.http.HttpRequestInterceptor
import org.apache.http.protocol.HttpContext

@Slf4j
class AuthSpec {

    String implementation
    String apiToken
    String username
    String password

    void implementation(String implementation) { this.implementation = implementation }

    void apiToken(String apiToken) { this.apiToken = apiToken }

    void username(String username) { this.username = username }

    void password(String password) { this.password = password }

    def apply(RESTClient restClient) {
        switch (implementation) {
            case "basic":
                log.info("Using Basic Auth for ${username}")
                restClient.client.addRequestInterceptor([
                        process: { HttpRequest httpRequest, HttpContext httpContext ->
                            httpRequest.addHeader('Authorization', 'Basic ' + "${username}:${password}".bytes.encodeBase64().toString())
                        }
                ] as HttpRequestInterceptor)
                break
            default:
                log.error("No auth implementation found")
                break
        }
    }
}
