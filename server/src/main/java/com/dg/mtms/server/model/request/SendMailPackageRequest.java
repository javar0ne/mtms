package com.dg.mtms.server.model.request;

public class SendMailPackageRequest {
    private String receiver;
    private String address;
    private Double weight;
    private Long userId;

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "SendMailPackageRequest{" +
            "receiver='" + receiver + '\'' +
            ", address='" + address + '\'' +
            ", weight=" + weight +
            ", userId=" + userId +
            '}';
    }
}
