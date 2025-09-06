package com.dg.mtms.client.operation;

import com.dg.mtms.client.client.MTMSClient;
import com.dg.mtms.common.request.UserCreateRequest;

import java.util.Scanner;

public class ProcessCreateUserOperation extends ProcessOperation {

    @Override
    public void process(Scanner scanner) {
        System.out.println("Registrazione utente.");
        System.out.println("Inserisci l'username: ");
        String username = scanner.nextLine();

        UserCreateRequest userCreateRequest = new UserCreateRequest(username);
        MTMSClient.getInstance().createUser(userCreateRequest);
    }
}
