package com.dg.mtms.client.operation;

import com.dg.mtms.client.client.MTMSClient;

import java.util.Scanner;

public class ProcessTrackPackageOperation extends ProcessOperation {
    public ProcessTrackPackageOperation(MTMSClient client) {
        super(client);
    }

    @Override
    public void process(Scanner scanner) {
        System.out.println("Traccia spedizione.");
        System.out.println("Inserisci il numero di spedizione: ");
        String trackingNumber = scanner.nextLine();

        client.trackPackage(trackingNumber);
    }
}
