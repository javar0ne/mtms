package com.dg.mtms.server.model.response;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;

public class HttpResponse {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private String version;
    private StatusCode code;
    private HashMap<String, String> headers = new HashMap<>();
    private String body;

    public static HttpResponse internalServerError() {
        HttpResponse response = new HttpResponse();
        response.setCode(StatusCode.INTERNAL_SERVER_ERROR);
        response.setVersion("HTTP/1.1");
        response.getHeaders().put("Content-Length", "0");
        response.getHeaders().put("Connection", "close");
        return response;
    }

    public static HttpResponse notFound() {
        HttpResponse response = new HttpResponse();
        response.setCode(StatusCode.NOT_FOUND);
        response.setVersion("HTTP/1.1");
        response.getHeaders().put("Content-Length", "0");
        response.getHeaders().put("Connection", "close");
        return response;
    }

    public static HttpResponse noContent() {
        HttpResponse response = new HttpResponse();
        response.setCode(StatusCode.NO_CONTENT);
        response.setVersion("HTTP/1.1");
        response.getHeaders().put("Connection", "close");
        response.getHeaders().put("Content-Length", "0");

        return response;
    }

    public static <T> HttpResponse ok(T body) {
        HttpResponse response = new HttpResponse();
        response.setCode(StatusCode.OK);
        response.setVersion("HTTP/1.1");
        response.getHeaders().put("Connection", "close");

        if(body != null) {
            try {
                String parsedBody = objectMapper.writeValueAsString(body);
                response.getHeaders().put("Content-Length", String.valueOf(parsedBody.length()));
                response.setBody(parsedBody);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        } else {
            response.getHeaders().put("Content-Length", "0");
        }

        return response;
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
