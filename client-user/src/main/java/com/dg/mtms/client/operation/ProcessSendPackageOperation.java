package com.dg.mtms.client.operation;

import com.dg.mtms.client.client.MTMSClient;
import com.dg.mtms.common.model.Dimension;
import com.dg.mtms.common.request.SendMailPackageRequest;
import com.dg.mtms.common.response.HttpResponse;
import com.dg.mtms.common.response.SendMailPackageResponse;
import com.dg.mtms.common.response.StatusCode;

import java.util.Scanner;


public class ProcessSendPackageOperation extends ProcessOperation {

    @Override
    public void process(Scanner scanner) {
        System.out.println("Invia nuovo pacco.");
        System.out.print("Inserisci destinatario: ");
        String receiver = scanner.nextLine();
        System.out.print("Inserisci l'indirizzo: ");
        String address = scanner.nextLine();
        System.out.print("Inserisci la dimensione (l x w x h): ");
        String[] size = scanner.nextLine().split("x");
        System.out.print("Inserisci il peso: ");
        String weight = scanner.nextLine();
        System.out.print("Inserisci l'username: ");
        String username = scanner.nextLine();

        HttpResponse response = MTMSClient.getInstance().sendPackage(
            new SendMailPackageRequest(
                receiver,
                address,
                new Dimension(Double.parseDouble(size[0]), Double.parseDouble(size[1]), Double.parseDouble(size[2])),
                Double.parseDouble(weight),
                username
            )
        );

        if(!StatusCode.OK.equals(response.getStatusCode())) {
            System.out.println("Errore nell'inserimento del pacco a sistema");
            return;
        }

        System.out.println("Pacco inserito a sistema");
        System.out.println("Numero di tracking: " + response.getParsedBody(SendMailPackageResponse.class).id());
    }
}
