package com.dg.mtms.client.operation;

import com.dg.mtms.client.client.MTMSClient;
import com.dg.mtms.common.response.HttpResponse;
import com.dg.mtms.common.response.SendMailPackageResponse;
import com.dg.mtms.common.response.StatusCode;

import java.util.Scanner;

public class ProcessTrackPackageOperation extends ProcessOperation {

    @Override
    public void process(Scanner scanner) {
        System.out.println("Traccia spedizione.");
        System.out.print("Inserisci il numero di spedizione: ");
        String trackingNumber = scanner.nextLine();

        HttpResponse response = MTMSClient.getInstance().trackPackage(trackingNumber);

        if(!StatusCode.OK.equals(response.getStatusCode())) {
            System.out.println("Errore nel tracciamento della spedizione.");
            return;
        }

        SendMailPackageResponse content = response.getParsedBody(SendMailPackageResponse.class);
        System.out.println("Tracciamento della spedizione:");
        System.out.println("Numero di spedizione: " + content.id());
        System.out.println("Destinatario: " + content.receiver());
        System.out.println("Indirizzo: " + content.address());
        System.out.println("Stato: " + content.status());
    }
}
