package com.dg.mtms.server.model.request;

import java.util.HashMap;

public class HttpRequest {
    private String method;
    private String endpoint;
    private String version;
    private HashMap<String, String> headers = new HashMap<>();
    private String body;

    public void setMethod(String method) {
        this.method = method;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    public String getMethod() {
        return method;
    }

    public void setBody(String body) {
        this.body = body;
    }
}


