package com.dg.mtms.server.model.request;

import com.dg.mtms.server.enums.HttpMethod;

import java.util.HashMap;

public class HttpRequest {
    private HttpMethod method;
    private String endpoint;
    private String version;
    private HashMap<String, String> headers = new HashMap<>();
    private HashMap<String, String> queryParams = new HashMap<>();
    private String body;

    public HttpMethod getMethod() {
        return method;
    }

    public void setMethod(HttpMethod method) {
        this.method = method;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;

        if(!this.endpoint.contains("?") || !this.method.equals(HttpMethod.GET)) return;

        String query = this.endpoint.split("\\?")[1];
        String[] params = query.split("&");
        for (String param : params) {
            String[] kv = param.split("=");
            this.queryParams.put(kv[0], kv[1]);
        }
    }

    public String getVersion() {
        return version;
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

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}


