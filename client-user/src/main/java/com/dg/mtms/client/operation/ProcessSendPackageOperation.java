package com.dg.mtms.client.operation;

import com.dg.mtms.client.client.MTMSClient;
import com.dg.mtms.common.model.Dimension;
import com.dg.mtms.common.request.SendMailPackageRequest;

import java.util.Scanner;


public class ProcessSendPackageOperation extends ProcessOperation {
    public ProcessSendPackageOperation(MTMSClient client) {
        super(client);
    }

    @Override
    public void process(Scanner scanner) {
        System.out.println("Invia nuovo pacco.");
        System.out.println("Inserisci destinatario: ");
        String receiver = scanner.nextLine();
        System.out.println("Inserisci l'indirizzo: ");
        String address = scanner.nextLine();
        System.out.println("Inserisci la dimensione (l x w x h): ");
        String[] size = scanner.nextLine().split("x");
        System.out.println("Inserisci il peso: ");
        String weight = scanner.nextLine();
        System.out.println("Inserisci l'username: ");
        String username = scanner.nextLine();

        client.sendPackage(
            new SendMailPackageRequest(
                receiver,
                address,
                new Dimension(Double.parseDouble(size[0]), Double.parseDouble(size[1]), Double.parseDouble(size[2])),
                Double.parseDouble(weight),
                username
            )
        );
    }
}
