package com.dg.mtms.client.manager.operation;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum Operation {
    UPDATE_DELIVERY_STATUS(1, "Update status spedizione"),
    EXIT(2, "Esci");

    private final int code;
    private final String description;

    Operation(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static String formatAsMenu() {
        return Arrays.stream(Operation.values())
            .map(operation -> String.format("%d. %s", operation.getCode(), operation.getDescription()))
            .collect(Collectors.joining("\n"));
    }

    public static boolean isValid(int value) {
        return Arrays.stream(Operation.values())
            .anyMatch(operation -> operation.getCode() == value);
    }
}
