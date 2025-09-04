package com.dg.mtms.server.model.request.dto;

import com.dg.mtms.server.model.Dimension;

public record SendMailPackageRequest(String receiver, String address, Dimension dimension, Double weight, String username){
    @Override
    public String toString() {
        return "SendMailPackageRequest{" +
            "receiver='" + receiver + '\'' +
            ", address='" + address + '\'' +
            ", dimension=" + dimension +
            ", weight=" + weight +
            ", username=" + username +
            '}';
    }
}
