package com.dg.mtms.client.operation;

import com.dg.mtms.client.client.MTMSClient;

import java.util.Scanner;

public class ProcessUserHistoryOperation extends ProcessOperation {

    @Override
    public void process(Scanner scanner) {
        System.out.println("Storico spedizioni.");
        System.out.println("Inserisci l'username: ");
        String username = scanner.nextLine();

        MTMSClient.getInstance().getUserHistory(username);
    }
}
