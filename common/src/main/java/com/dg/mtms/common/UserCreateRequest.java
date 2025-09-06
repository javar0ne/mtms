package com.dg.mtms.common;

public record UserCreateRequest(String username) {
    @Override
    public String toString() {
        return "UserCreateRequest{" +
            "username='" + username + '\'' +
            '}';
    }
}
