package com.dg.mtms.client.manager.client.request;

import java.util.HashMap;
import java.util.Objects;

public class HttpRequest {
    private String path;
    private String host;
    private String method;
    private String body;
    private HashMap<String, String> headers = new HashMap<>();
    private String version;

    public HttpRequest() {}

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public HashMap<String, String> getHeaders() {
        return headers;
    }

    public void setHeaders(HashMap<String, String> headers) {
        this.headers = headers;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void addHeader(String key, String value) {
        Objects.requireNonNull(key);
        Objects.requireNonNull(value);
        headers.put(key, value);
    }

    /*
    *   METHOD /path HTTP/1.1\r\n
    *   Host: hostname\r\n
    *   Header-Name: Header-Value\r\n
    *   \r\n
    *   [optional body data]
    * */
    public String toString() {
        String request = method + " " + path + " " + version + "\r\n";
        request += "Host: " + host + "\r\n";
        for (String key : headers.keySet()) {
            request += key + ": " + headers.get(key) + "\r\n";
        }
        request += "\r\n";
        if (body != null) {
            request += body;
        }
        return request;
    }
}
