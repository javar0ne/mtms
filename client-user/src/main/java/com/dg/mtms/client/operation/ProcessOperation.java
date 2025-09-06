package com.dg.mtms.client.operation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public abstract class ProcessOperation {
    public abstract void process(Scanner scanner);

    protected void readResponse(Socket socket) throws IOException {
        InputStream inputStream = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        reader.close();
    }

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
