package com.dg.mtms.client.manager.operation;

import java.util.Scanner;

public class ProcessExitOperation extends ProcessOperation {
    @Override
    public void process(Scanner scanner) {
        System.out.println("Arrivederci!");
    }

}
