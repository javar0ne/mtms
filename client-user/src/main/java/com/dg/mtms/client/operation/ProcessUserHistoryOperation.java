package com.dg.mtms.client.operation;

import com.dg.mtms.client.client.MTMSClient;
import com.dg.mtms.common.response.HttpResponse;
import com.dg.mtms.common.response.SendMailPackageResponse;
import com.dg.mtms.common.response.StatusCode;

import java.util.List;
import java.util.Scanner;

public class ProcessUserHistoryOperation extends ProcessOperation {

    @Override
    public void process(Scanner scanner) {
        System.out.println("Storico spedizioni.");
        System.out.print("Inserisci l'username: ");
        String username = scanner.nextLine();

        HttpResponse response = MTMSClient.getInstance().getUserHistory(username);

        if(!StatusCode.OK.equals(response.getStatusCode())) {
            System.out.println("Errore nel recupero dello storico.");
            return;
        }

        List<SendMailPackageResponse> packages = response.getParsedListBody(SendMailPackageResponse.class);

        if(packages == null || packages.isEmpty()) {
            System.out.println("Nessuna spedizione trovata per l'utente " + username);
        }

        System.out.println("--------------------------------");
        for(SendMailPackageResponse packageResponse : packages) {
            System.out.println("Numero di spedizione: " + packageResponse.id());
            System.out.println("Destinatario: " + packageResponse.receiver());
            System.out.println("Indirizzo: " + packageResponse.address());
            System.out.println("Stato: " + packageResponse.status());
            System.out.println("--------------------------------");
        }
    }
}
