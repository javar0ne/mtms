package com.dg.mtms.client.manager.operation;

import java.util.Scanner;

public abstract class ProcessOperation {
    public abstract void process(Scanner scanner);

    public static ProcessOperation getInstance(int operationCode) {
        if(Operation.UPDATE_DELIVERY_STATUS.getCode() == operationCode) {
            return new ProcessUpdatePackageStatusOperation();
        } else if(Operation.EXIT.getCode() == operationCode) {
            return new ProcessExitOperation();
        }

        throw new IllegalArgumentException("Invalid operation code");
    }
}
