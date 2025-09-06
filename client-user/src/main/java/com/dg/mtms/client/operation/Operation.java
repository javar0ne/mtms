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
