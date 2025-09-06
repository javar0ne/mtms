package com.dg.mtms.client.operation;

import com.dg.mtms.client.client.MTMSClient;
import com.dg.mtms.common.model.Dimension;

import java.util.Scanner;

public class ProcessCalculateFeeOperation extends ProcessOperation {

    @Override
    public void process(Scanner scanner) {
        System.out.println("Calcola tariffa.");
        System.out.println("Inserisci le dimensioni (l x w x h): ");
        String[] size = scanner.nextLine().split("x");

        MTMSClient.getInstance().calculateFee(
            new Dimension(
                Double.parseDouble(size[0]),
                Double.parseDouble(size[1]),
                Double.parseDouble(size[2])
            )
        );
    }
}
