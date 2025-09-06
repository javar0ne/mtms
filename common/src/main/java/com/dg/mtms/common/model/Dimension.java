package com.dg.mtms.common.model;

public class Dimension {
    private Double length;
    private Double width;
    private Double height;

    public Dimension(Double length, Double width, Double height) {
        this.length = length;
        this.width = width;
        this.height = height;
    }

    public Dimension() {}

    public Double getLength() {
        return length;
    }

    public void setLength(Double length) {
        this.length = length;
    }

    public Double getWidth() {
        return width;
    }

    public void setWidth(Double width) {
        this.width = width;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    @Override
    public String toString() {
        return "Dimension{" +
                "length=" + length +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
