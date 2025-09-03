package com.dg.mtms.server.enums;

import java.util.Arrays;

public enum HttpMethod {
    GET, POST, PATCH;

    public static boolean isValid(String method) {
        return Arrays.stream(HttpMethod.values())
            .map(HttpMethod::name)
            .anyMatch(method::equals);
    }
}
