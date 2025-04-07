package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
        try (ServerSocket serverSocket = new ServerSocket(8080)){
            System.out.println("Server started");
            while(true) {
                System.out.println("Waiting for client connection");
                Socket socket = serverSocket.accept();
                executorService.submit(new Dispatcher(socket));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            executorService.shutdown();
        }
    }
}
