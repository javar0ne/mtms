package com.dg.mtms.common.request;


import com.dg.mtms.common.model.Dimension;

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
