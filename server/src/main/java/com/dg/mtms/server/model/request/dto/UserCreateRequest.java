package com.dg.mtms.server.model.request.dto;

public record UserCreateRequest(String username, String password) {
    @Override
    public String toString() {
        return "UserCreateRequest{" +
            "username='" + username + '\'' +
            '}';
    }
}
