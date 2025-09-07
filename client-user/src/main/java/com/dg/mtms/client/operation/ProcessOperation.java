package com.dg.mtms.client.operation;

import java.util.Scanner;

public abstract class ProcessOperation {
    public abstract void process(Scanner scanner);

    public static ProcessOperation getInstance(int operationCode) {
        if(Operation.CREATE_USER.getCode() == operationCode) {
            return new ProcessCreateUserOperation();
        } else if(Operation.SEND_PACKAGE.getCode() == operationCode) {
            return new ProcessSendPackageOperation();
        } else if(Operation.TRACK_PACKAGE.getCode() == operationCode) {
            return new ProcessTrackPackageOperation();
        } else if(Operation.CALCULATE_FEE.getCode() == operationCode) {
            return new ProcessCalculateFeeOperation();
        } else if(Operation.GET_HISTORY.getCode() == operationCode) {
            return new ProcessUserHistoryOperation();
        } else if(Operation.EXIT.getCode() == operationCode) {
            return new ProcessExitOperation();
        }

        throw new IllegalArgumentException("Invalid operation code");
    }
}
