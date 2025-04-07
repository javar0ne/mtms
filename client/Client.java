package client;

import java.io.IOException;
import java.net.Socket;

public class Client {

    public static void main(String[] args) {
        try (Socket socket = new Socket("127.0.0.1", 8080)) {
            //parse input in http request
            socket.getOutputStream().write("GET /send-package".getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
