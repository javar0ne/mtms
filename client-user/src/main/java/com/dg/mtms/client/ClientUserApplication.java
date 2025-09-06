package com.dg.mtms.client;

import com.dg.mtms.client.operation.Operation;
import com.dg.mtms.client.operation.ProcessOperation;

import java.util.Scanner;

public class ClientUserApplication {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int operationCode;
        do {
            System.out.println("Benvenuto ad MTMS:");
            System.out.println(Operation.formatAsMenu());
            System.out.print("Seleziona un'operazione: ");
            operationCode = Integer.parseInt(scanner.nextLine());
            if(Operation.isValid(operationCode)) {
                ProcessOperation.getInstance(operationCode)
                    .process(scanner);
            } else {
                System.out.println("Opzione non valida!");
            }

        } while (operationCode != Operation.EXIT.getCode());
    }
}
