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
        return convertQueryParam(parameter.getType(), httpRequest.getQueryParam(value));
    }

    private Object convertQueryParam(Class<?> type, String value) {
        if(type.equals(Integer.class)) {
            return Integer.parseInt(value);
        } else if(type.equals(Long.class)) {
            return Long.parseLong(value);
        } else if(type.equals(Double.class)) {
            return Double.parseDouble(value);
        } else {
            return value;
        }
    }
}
