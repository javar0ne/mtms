package com.dg.mtms.common.request;

public record UserCreateRequest(String username) {
    @Override
    public String toString() {
        return "UserCreateRequest{" +
            "username='" + username + '\'' +
            '}';
    }
}
