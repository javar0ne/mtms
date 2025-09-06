package com.dg.mtms.common.response;

import java.util.Arrays;

public enum StatusCode {
    OK(200, "OK"),
    NO_CONTENT(204, "No Content"),
    NOT_FOUND(404, "Not Found"),
    BAD_REQUEST(400, "Bad Request"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error");

    private final int code;
    private final String message;

    StatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public static StatusCode valueOf(int code) {
        return Arrays.stream(values())
            .filter(statusCode -> statusCode.getCode() == code)
            .findFirst()
            .orElseThrow(() -> new IllegalArgumentException("Invalid status code: " + code));
    }

    public String toString() {
        return code + " " + message;
    }
}
