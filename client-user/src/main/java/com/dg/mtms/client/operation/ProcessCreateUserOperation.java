package com.dg.mtms.client.operation;

import com.dg.mtms.client.client.MTMSClient;
import com.dg.mtms.common.request.UserCreateRequest;
import com.dg.mtms.common.response.HttpResponse;
import com.dg.mtms.common.response.StatusCode;

import java.util.Scanner;

public class ProcessCreateUserOperation extends ProcessOperation {

    @Override
    public void process(Scanner scanner) {
        System.out.println("Registrazione utente.");
        System.out.print("Inserisci l'username: ");
        String username = scanner.nextLine();

        UserCreateRequest userCreateRequest = new UserCreateRequest(username);
        HttpResponse response = MTMSClient.getInstance().createUser(userCreateRequest);

        if(!StatusCode.OK.equals(response.getStatusCode())) {
            System.out.println("Registrazione fallita.");
            return;
        }

        System.out.println("Registrazione avvenuta con successo.");
    }
}
