package com.dg.mtms.server.dispatcher;

import com.dg.mtms.server.Singleton;
import com.dg.mtms.server.annnotation.Request;
import com.dg.mtms.server.enums.HttpMethod;
import com.dg.mtms.server.model.request.HttpRequest;
import com.dg.mtms.server.util.HttpParser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

public class RequestDispatcher implements Runnable {
    private final Map<String, Class<?>> controllers;
    private final Socket socket;

    public RequestDispatcher(Map<String, Class<?>> controllers, Socket socket) {
        this.controllers = controllers;
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            HttpParser parser = new HttpParser();
            HttpRequest httpRequestParsed = parser.parse(socketReader);

            if(!HttpMethod.isValid(httpRequestParsed.getMethod())) {
                throw new IllegalStateException("Unknown HTTP method: " + httpRequestParsed.getMethod());
            }

            Optional<String> controllerBasePath = controllers.keySet()
                .stream()
                .filter(httpRequestParsed.getEndpoint()::startsWith)
                .findFirst();
            if(controllerBasePath.isEmpty()) {
                throw new IllegalStateException("No controller found for " + httpRequestParsed.getEndpoint());
            }

            String endpointPath = httpRequestParsed.getEndpoint().substring(controllerBasePath.get().length());
            Optional<Method> matchedMethod = Arrays.stream(controllers.get(controllerBasePath.get()).getMethods())
                .filter(m ->
                    m.isAnnotationPresent(Request.class) &&
                    m.getDeclaredAnnotation(Request.class)
                        .endpoint()
                        .equals(endpointPath) &&
                    m.getDeclaredAnnotation(Request.class)
                        .method()
                        .equals(httpRequestParsed.getMethod())
                )
                .findFirst();

            if(matchedMethod.isEmpty()) {
                throw new IllegalStateException("Endpoint not found");
            }

            matchedMethod.get().invoke(Singleton.getInstance(controllers.get(controllerBasePath.get())));
        } catch (IOException | InvocationTargetException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
