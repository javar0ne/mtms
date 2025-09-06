package com.dg.mtms.client.operation;

import com.dg.mtms.client.client.MTMSClient;

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

    public static ProcessOperation getInstance(Integer operationCode) {
        // TODO: match with enum operation codes
        return switch (operationCode) {
            case 1 -> new ProcessCreateUserOperation();
            case 2 -> new ProcessSendPackageOperation();
            case 3 -> new ProcessTrackPackageOperation();
            case 4 -> new ProcessCalculateFeeOperation();
            case 5 -> new ProcessUserHistoryOperation();
            case 6 -> new ProcessExitOperation();
            default -> throw new IllegalArgumentException("Invalid operation code");
        };
    }
}
