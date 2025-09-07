package com.dg.mtms.client.manager.operation;

import com.dg.mtms.client.manager.client.MTMSClient;
import com.dg.mtms.common.model.PackageStatus;
import com.dg.mtms.common.request.UpdatePackageStatusRequest;
import com.dg.mtms.common.response.HttpResponse;
import com.dg.mtms.common.response.StatusCode;

import java.util.Scanner;

public class ProcessUpdatePackageStatusOperation extends ProcessOperation {

    @Override
    public void process(Scanner scanner) {
        System.out.println("Update status spedizione.");
        System.out.print("Inserisci numero spedizione: ");
        Long packageNumber = Long.parseLong(scanner.nextLine());
        System.out.print("Inserisci stato spedizione: ");
        PackageStatus status = PackageStatus.valueOf(scanner.nextLine());

        UpdatePackageStatusRequest updatePackageStatusRequest = new UpdatePackageStatusRequest(packageNumber,status.name());
        HttpResponse response = MTMSClient.getInstance().updatePackageStatus(updatePackageStatusRequest);

        if(!StatusCode.NO_CONTENT.equals(response.getStatusCode())) {
            System.out.println("Update spedizione fallita.");
            return;
        }

        System.out.println("Update status spedizione avvenuto con successo.");
    }
}
