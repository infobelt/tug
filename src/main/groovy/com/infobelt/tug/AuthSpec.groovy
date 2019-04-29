package com.infobelt.tug

import groovy.util.logging.Slf4j
import groovyx.net.http.RESTClient

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
            case "basicAuth":
                restClient.auth.basic(username, password)
                break
            default:
                break
        }
    }
}
