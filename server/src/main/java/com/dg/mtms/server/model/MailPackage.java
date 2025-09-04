package com.dg.mtms.server.model;

public class MailPackage {
    private Long id;
    private String receiver;
    private String address;
    private Dimension dimension;
    private Double weight;
    private PackageStatus status;
    private Long userId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public PackageStatus getStatus() {
        return status;
    }

    public void setStatus(PackageStatus status) {
        this.status = status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "MailPackage{" +
            "id=" + id +
            ", receiver='" + receiver + '\'' +
            ", address='" + address + '\'' +
            ", weight=" + weight +
            ", status=" + status +
            ", userId=" + userId +
            '}';
    }

    public Dimension getDimension() {
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }
}
