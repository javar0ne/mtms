package com.dg.mtms.client.manager;

import com.dg.mtms.client.manager.operation.Operation;
import com.dg.mtms.client.manager.operation.ProcessOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Scanner;


public class ClientManagerApplication {

    private static final Logger logger = LoggerFactory.getLogger(ClientManagerApplication.class);

    public static void main(String[] args) {
        System.out.println("Benvenuto ad MTMS:");
        startClient();
    }
    private static void startClient() {
        Scanner scanner = new Scanner(System.in);
        try {
            int operationCode;
            do {
                System.out.println(Operation.formatAsMenu());
                System.out.print("Seleziona un'operazione: ");
                operationCode = Integer.parseInt(scanner.nextLine());
                if (Operation.isValid(operationCode)) {
                    ProcessOperation.getInstance(operationCode)
                            .process(scanner);
                } else {
                    System.out.println("Opzione non valida!");
                }
            } while (operationCode != Operation.EXIT.getCode());
        } catch (Exception e) {
            logger.error("Errore durante l'esecuzione del client!", e);
            startClient();
        } finally {
            scanner.close();
        }
    }

}
