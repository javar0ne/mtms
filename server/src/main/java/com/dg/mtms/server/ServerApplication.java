package com.dg.mtms.server;

import com.dg.mtms.server.controller.MailController;
import com.dg.mtms.server.dispatcher.RequestDispatcher;
import com.dg.mtms.server.repository.MailRepository;
import com.dg.mtms.server.service.MailService;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerApplication {
    private static final Map<String, Class<?>> controllers = new HashMap<>();

    public static void main(String[] args) {
        try {
            initSingletons();
            validateAndPopulateControllers();
            runServer();
        } catch (Exception e) {
            System.err.println(e.getMessage());
        }
    }

    private static void initSingletons() {
        MailRepository.createInstance();
        MailService.createInstance(MailRepository.getInstance());
        MailController.createInstance(MailService.getInstance());
    }

    private static void validateAndPopulateControllers() {
        // scan all controllers
        // check if basePath is present for 2 controllers: throw an exception if it's the case
        // create a map with class references
        controllers.put("/mail", MailController.class);
    }

    private static void runServer() {
        try (
            ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
            ServerSocket serverSocket = new ServerSocket(8080)
        ) {
            System.out.println("Server started");
            while(true) {
                System.out.println("Waiting for client connection");
                Socket socket = serverSocket.accept();
                executorService.submit(new RequestDispatcher(controllers, socket));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
