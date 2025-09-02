package com.dg.mtms.server.model.request.injector;

import com.dg.mtms.server.annotation.RequestParam;
import com.dg.mtms.server.model.request.HttpRequest;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Parameter;
import java.util.Optional;

public class RequestParamInjector implements  RequestInjector {

    private final HttpRequest httpRequest;

    public RequestParamInjector(HttpRequest httpRequest) {
        this.httpRequest = httpRequest;
    }

    @Override
    public Object inject(Parameter parameter){
        String value = Optional.of(parameter)
                .map(p -> p.getDeclaredAnnotation(RequestParam.class))
                .map(RequestParam::value)
                .filter(StringUtils::isNotBlank)
                .orElseThrow(() -> new IllegalArgumentException("RequestParam annotation is required"));
        return httpRequest.getQueryParam(value);
    }
}
