package com.dg.mtms.server;

import com.dg.mtms.server.annnotation.Controller;
import com.dg.mtms.server.controller.MailController;
import com.dg.mtms.server.dispatcher.RequestDispatcher;
import com.dg.mtms.server.repository.MailRepository;
import com.dg.mtms.server.service.MailService;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerApplication {
    private static final Map<String, Class<?>> controllers = new HashMap<>();

    public static void main(String[] args) {
        try {
            initSingletons();
            validateAndPopulateControllers(ServerApplication.class.getPackage().getName());
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

    private static void validateAndPopulateControllers(String packageName){
        try{
            String path = packageName.replace(".","/");
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            URL resource = classLoader.getResource(path);
            File directory = new File(resource.toURI());
            for (File file : directory.listFiles()) {
                if (file.getName().endsWith(".class")) {
                    String className = packageName + "." + file.getName().replace(".class", "");
                    Class<?> clazz = Class.forName(className);
                    if (clazz.isAnnotationPresent(Controller.class)) {
                        String basePath = clazz.getDeclaredAnnotation(Controller.class).basePath();
                        if(controllers.containsKey(basePath)) {
                            throw new RuntimeException(basePath + " has already been found");
                        }
                        controllers.put(basePath, clazz);
                    }
                } else {
                    validateAndPopulateControllers(packageName+"."+file.getName());
                }
            }
        } catch (URISyntaxException | ClassNotFoundException e){
            System.err.println(e.getMessage());
        }
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
