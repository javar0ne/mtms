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

import java.io.IOException;
import java.net.*;
import java.nio.file.*;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

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

    private static void validateAndPopulateControllers(String packageName) throws IOException, URISyntaxException {
        String packagePath = packageName.replace('.', '/');
        URL resource = Thread.currentThread().getContextClassLoader().getResource(packagePath);
        Path packageDir;

        if (resource == null) {
            return;
        }

        URI uri = resource.toURI();

        if (uri.getScheme().equals("jar")) {
            // Running from JAR
            try (FileSystem fileSystem = FileSystems.newFileSystem(uri, Collections.emptyMap())) {
                packageDir = fileSystem.getPath(packagePath);
                findClassesInPath(packageDir, packageName);
            }
        } else {
            // Running from file system
            packageDir = Paths.get(uri);
            findClassesInPath(packageDir, packageName);
        }
    }

    private static void findClassesInPath(Path packageDir, String packageName) throws IOException {
        try (Stream<Path> files = Files.walk(packageDir)) {
            files.forEach(path -> {
                if(path.toString().endsWith(".class")) {
                    String className = getClassName(path, packageDir, packageName);
                    try {
                        Class<?> clazz = Class.forName(className);
                        if (clazz.isAnnotationPresent(Controller.class)) {
                            String basePath = clazz.getDeclaredAnnotation(Controller.class).basePath();
                            if(controllers.containsKey(basePath)) {
                                throw new RuntimeException(basePath + " has been declared twice!");
                            }
                            controllers.put(basePath, clazz);
                        }
                    } catch (ClassNotFoundException _) {}
                }
            });
        }
    }

    private static String getClassName(Path classFile, Path packageDir, String packageName) {
        Path relativePath = packageDir.relativize(classFile);
        String className = relativePath.toString()
            .replace('/', '.')
            .replace('\\', '.')
            .replace(".class", "");
        return packageName + "." + className;
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
