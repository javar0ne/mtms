package com.dg.mtms.server.model.request.injector;

import com.dg.mtms.server.model.request.HttpRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.lang.reflect.Parameter;

public class RequestBodyInjector implements RequestInjector{

    private final ObjectMapper objectMapper;
    private final HttpRequest httpRequest;

    public RequestBodyInjector(ObjectMapper objectMapper, HttpRequest httpRequest) {
        this.objectMapper = objectMapper;
        this.httpRequest = httpRequest;
    }
    @Override
    public Object inject(Parameter parameter) {
        try {
            return objectMapper.readValue(httpRequest.getBody(), parameter.getType());
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
}
