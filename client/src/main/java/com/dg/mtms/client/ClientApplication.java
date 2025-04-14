package com.dg.mtms.client;

import java.io.IOException;
import java.net.Socket;

public class ClientApplication {

    public static void main(String[] args) {
        try (Socket socket = new Socket("127.0.0.1", 8080)) {
            socket.getOutputStream().write("POST /mail/send-package".getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
