package com.dg.mtms.client.operation;

import com.dg.mtms.client.client.MTMSClient;
import com.dg.mtms.common.Dimension;

import java.util.Scanner;

public class ProcessCalculateFeeOperation extends ProcessOperation {
    public ProcessCalculateFeeOperation(MTMSClient client) {
        super(client);
    }

    @Override
    public void process(Scanner scanner) {
        System.out.println("Calcola tariffe.");
        System.out.println("Inserisci le dimensioni (l x w x h): ");
        String[] size = scanner.nextLine().split("x");

        client.calculateFee(
            new Dimension(
                Double.parseDouble(size[0]),
                Double.parseDouble(size[1]),
                Double.parseDouble(size[2])
            )
        );
    }
}
