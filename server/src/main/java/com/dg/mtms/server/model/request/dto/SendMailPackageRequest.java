package com.dg.mtms.server.model.request.dto;

public record SendMailPackageRequest(String receiver, String address, Double weight, String username){
    @Override
    public String toString() {
        return "SendMailPackageRequest{" +
            "receiver='" + receiver + '\'' +
            ", address='" + address + '\'' +
            ", weight=" + weight +
            ", username=" + username +
            '}';
    }
}
