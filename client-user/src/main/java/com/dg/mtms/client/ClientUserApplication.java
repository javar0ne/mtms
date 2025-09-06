package com.dg.mtms.client;

import com.dg.mtms.client.client.MTMSClient;
import com.dg.mtms.client.operation.Operation;
import com.dg.mtms.client.operation.ProcessOperation;

import java.util.Scanner;

public class ClientUserApplication {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int optionChoice;
        do {
            System.out.println("Benvenuto ad MTMS:");
            System.out.println(Operation.formatAsMenu());
            System.out.println("Seleziona un'operazione: ");
            optionChoice = Integer.parseInt(scanner.nextLine());
            if(optionChoice > 0 && optionChoice < 6 ) {
                ProcessOperation.getInstance(optionChoice, MTMSClient.getInstance())
                    .process(scanner);
            } else {
                System.out.println("Opzione non valida!");
            }

        } while (optionChoice != 6);
    }
}
