package com.dg.mtms.client.operation;

import com.dg.mtms.client.client.MTMSClient;
import com.dg.mtms.common.UserCreateRequest;

import java.util.Scanner;

public class ProcessCreateUserOperation extends ProcessOperation {
    public ProcessCreateUserOperation(MTMSClient client) {
        super(client);
    }

    @Override
    public void process(Scanner scanner) {
        System.out.println("Crea nuovo utente.");
        System.out.println("Inserisci l'username: ");
        String username = scanner.nextLine();

        UserCreateRequest userCreateRequest = new UserCreateRequest(username);
        client.createUser(userCreateRequest);
    }
}
