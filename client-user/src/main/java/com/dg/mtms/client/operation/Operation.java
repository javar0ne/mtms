package com.dg.mtms.client.operation;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum Operation {
    CREATE_USER(1, "Registrazione utente"),
    SEND_PACKAGE(2, "Invio pacco"),
    TRACK_PACKAGE(3, "Traccia spedizione"),
    CALCULATE_FEE(4, "Calcolo tariffe"),
    GET_HISTORY(5, "Storico spedizioni"),
    EXIT(6, "Esci");

    private final int value;
    private final String description;

    Operation(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public String getDescription() {
        return description;
    }

    public static String formatAsMenu() {
        return Arrays.stream(Operation.values())
            .map(operation -> String.format("%d. %s", operation.getValue(), operation.getDescription()))
            .collect(Collectors.joining("\n"));
    }

    public static boolean isValid(int value) {
        return Arrays.stream(Operation.values())
            .anyMatch(operation -> operation.getValue() == value);
    }
}
