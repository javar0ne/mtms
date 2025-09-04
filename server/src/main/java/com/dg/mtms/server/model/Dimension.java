package com.dg.mtms.server.model;

public record Dimension (Double length, Double width, Double height ) {

    @Override
    public String toString() {
        return "Dimension{" +
                "length=" + length +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
