package com.dg.mtms.server;

import com.dg.mtms.server.annotation.Controller;
import com.dg.mtms.server.controller.MailPackageController;
import com.dg.mtms.server.controller.UserController;
import com.dg.mtms.server.dispatcher.RequestDispatcher;
import com.dg.mtms.server.repository.MailPackageRepository;
import com.dg.mtms.server.repository.UserRepository;
import com.dg.mtms.server.service.MailPackageService;
import com.dg.mtms.server.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServerApplication {
    private static final Logger logger = LoggerFactory.getLogger(ServerApplication.class);
    private static final Map<String, Class<?>> controllers = new HashMap<>();

    public static void main(String[] args) {
        try {
            initSingletons();
            validateAndPopulateControllers(ServerApplication.class.getPackage().getName());
            runServer();
        } catch (Exception e) {
            logger.error("Error while initializing server!", e);
        }
    }

    private static void initSingletons() {
        UserRepository.createInstance();
        UserService.createInstance(UserRepository.getInstance());
        UserController.createInstance(UserService.getInstance());
        MailPackageRepository.createInstance();
        MailPackageService.createInstance(UserService.getInstance(), MailPackageRepository.getInstance());
        MailPackageController.createInstance(MailPackageService.getInstance());
    }

    private static void validateAndPopulateControllers(String packageName) {
        try {
            String path = packageName.replace(".","/");
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            URL resource = classLoader.getResource(path);
            File directory;
            File[] files;

            if(resource == null) return;

            if(resource.toURI().getScheme().equals("jar")) {
                FileSystem fileSystem = FileSystems.newFileSystem(resource.toURI(), Collections.emptyMap());
                directory = fileSystem.getPath(path).toFile();
            } else {
                directory = new File(resource.toURI());
            }
            files = directory.listFiles();

            if(files == null) return;

            for (File file : files) {
                if (file.getName().endsWith(".class")) {
                    String className = packageName + "." + file.getName().replace(".class", "");
                    Class<?> clazz = Class.forName(className);
                    if (clazz.isAnnotationPresent(Controller.class)) {
                        String basePath = clazz.getDeclaredAnnotation(Controller.class).basePath();
                        if(controllers.containsKey(basePath)) {
                            throw new RuntimeException(basePath + " has been declared twice!");
                        }
                        controllers.put(basePath, clazz);
                    }
                } else {
                    validateAndPopulateControllers(packageName + "." + file.getName());
                }
            }
        } catch (URISyntaxException | ClassNotFoundException | IOException e) {
            logger.error("Error while validating and populating controllers!", e);
        }
    }

    private static void runServer() {
        try (
            ExecutorService executorService = Executors.newVirtualThreadPerTaskExecutor();
            ServerSocket serverSocket = new ServerSocket(8080)
        ) {
            logger.info("Server started!");
            //noinspection InfiniteLoopStatement
            while(true) {
                logger.info("Waiting for client connection..");
                Socket socket = serverSocket.accept();
                executorService.submit(new RequestDispatcher(controllers, socket));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
