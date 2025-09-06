package com.dg.mtms.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Scanner;

public class ClientApplication {

    public static void main(String[] args) {
        try (Socket socket = new Socket("127.0.0.1", 8080)) {
            Scanner scanner = new Scanner(System.in);
            int optionChoice = 0;
            while (optionChoice != 6) {
                System.out.println("""
                        Seleziona un'operazione:\s
                        1. Creazione utente
                        2. Invio pacco
                        3. Traccia spedizione
                        4. Calcolo tariffe
                        5. Storico spedizioni
                        6. Esci""");
                optionChoice = Integer.parseInt(scanner.nextLine());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void createUser(Scanner scanner, Socket socket) throws IOException {
        System.out.println("Crea nuovo utente.");
        System.out.println("Inserisci l'username: ");
        String username = scanner.nextLine();
        System.out.println("Inserisci la password: ");
        String password = scanner.nextLine();
        String bodyRequest = "{\"username\": \"" + username + "\", \"password\": \"" + password + "\"}";
        String userRequest = "POST /v1/user HTTP/1.1\r\n" +
                "Content-Type: application/json\r\n" +
                "Content-Length: " + bodyRequest.length() + "\r\n\r\n" + bodyRequest;
        socket.getOutputStream().write(userRequest.getBytes());
        readResponse(socket);
    }

    private static void readResponse(Socket socket) throws IOException {
        InputStream inputStream = socket.getInputStream();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
        String line;
        while((line = reader.readLine()) != null) {
            System.out.println(line);
        }
        reader.close();
    }

    private static void sendPackage(Scanner scanner, Socket socket) throws IOException {
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
        String bodyRequest = "{" +
                "\"receiver\": \"" + receiver + "\",\n" +
                "\"address\": \"" + address + "\",\n" +
                "\"dimension\": \"{ \"length\":" + size[0] + ", \"width\":" + size[1] + ", \"height\":" + size[2] + "}\",\n" +
                "\"weight\": \"" + weight + "\",\n" +
                "\"username\": \"" + username + "\"" +
                "}";
        String sendPackageRequest = "POST /v1/package/send-package HTTP/1.1\r\n" +
                "Content-Type: application/json\r\n" +
                "Content-Length: " + bodyRequest.length() + "\r\n\r\n" + bodyRequest;
        socket.getOutputStream().write(sendPackageRequest.getBytes());
        readResponse(socket);
    }

    private static void trackPackage(Scanner scanner, Socket socket) throws IOException {
        System.out.println("Traccia spedizione.");
        System.out.println("Inserisci il numero di spedizione: ");
        String trackingNumber = scanner.nextLine();
        String trackPackageRequest = "GET /v1/package/track-package?packageNumber=" + trackingNumber + " HTTP/1.1\r\n" +
                "Content-Type: application/json\r\n";
        socket.getOutputStream().write(trackPackageRequest.getBytes());
        readResponse(socket);
    }


    private static void calculateFee(Scanner scanner, Socket socket) throws IOException {
        System.out.println("Calcola tariffe.");
        System.out.println("Inserisci le dimensioni (l x w x h): ");
        String[] size = scanner.nextLine().split("x");
        String calculateFeeRequest = "GET /v1/package/calculate-fee?length=" + size[0] + "&width=" + size[1] + "&height=" + size[2] + " HTTP/1.1\r\n" +
                "Content-Type: application/json\r\n";
        socket.getOutputStream().write(calculateFeeRequest.getBytes());
        readResponse(socket);
    }

    private static void getHistory(Scanner scanner, Socket socket) throws IOException {
        System.out.println("Storico spedizioni.");
        System.out.println("Inserisci l'username: ");
        String username = scanner.nextLine();
        String getHistoryRequest = "GET /v1/package/user?username=" + username + " HTTP/1.1\r\n" +
                "Content-Type: application/json\r\n";
        socket.getOutputStream().write(getHistoryRequest.getBytes());
        readResponse(socket);
    }
}
