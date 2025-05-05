package com.dg.mtms.server.model.response;

import java.util.HashMap;

public class HttpResponse {
    private String version;
    private StatusCode code;
    private HashMap<String, String> headers = new HashMap<>();
    private String body;

    public static HttpResponse notFound() {
        HttpResponse response = new HttpResponse();
        response.setCode(StatusCode.NOT_FOUND);
        response.setVersion("HTTP/1.1");
        response.getHeaders().put("Content-Length", "0");
        response.getHeaders().put("Connection", "close");
        return response;
    }

    public static HttpResponse ok(String body) {
        HttpResponse response = new HttpResponse();
        response.setCode(StatusCode.OK);
        response.setVersion("HTTP/1.1");
        response.getHeaders().put("Connection", "close");

        if(body != null && !body.isEmpty()) {
            response.getHeaders().put("Content-Length", String.valueOf(body.length()));
            response.setBody(body);
        } else {
            response.getHeaders().put("Content-Length", "0");
        }

        return response;
    }

    public static HttpResponse ok() {
        return ok(null);
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public StatusCode getCode() {
        return code;
    }

    public void setCode(StatusCode code) {
        this.code = code;
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

    @Override
    public String toString() {
        String result = version + " " + code + "\r\n";
        for (String key : headers.keySet()) {
            result += key + ": " + headers.get(key) + "\r\n";
        }
        result += "\r\n";
        if(body != null) {
            result += body;
        }
        return result;
    }
}
