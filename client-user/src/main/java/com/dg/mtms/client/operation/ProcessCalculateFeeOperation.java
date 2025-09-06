package com.dg.mtms.client.operation;

import com.dg.mtms.client.client.MTMSClient;
import com.dg.mtms.common.model.Dimension;
import com.dg.mtms.common.response.HttpResponse;
import com.dg.mtms.common.response.PackageFeeResponse;
import com.dg.mtms.common.response.StatusCode;

import java.util.Scanner;

public class ProcessCalculateFeeOperation extends ProcessOperation {

    @Override
    public void process(Scanner scanner) {
        System.out.println("Calcola tariffa.");
        System.out.print("Inserisci le dimensioni (l x w x h): ");
        String[] size = scanner.nextLine().split("x");

        HttpResponse response = MTMSClient.getInstance().calculateFee(
            new Dimension(
                Double.parseDouble(size[0]),
                Double.parseDouble(size[1]),
                Double.parseDouble(size[2])
            )
        );

        if(!StatusCode.OK.equals(response.getStatusCode())) return;

        PackageFeeResponse content = response.getParsedBody(PackageFeeResponse.class);
        System.out.println("La tariffa per il la spedizione del pacco e': " + content.fee());
    }
}
