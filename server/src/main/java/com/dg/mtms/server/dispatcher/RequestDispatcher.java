package com.dg.mtms.server.dispatcher;

import com.dg.mtms.server.Singleton;
import com.dg.mtms.server.annnotation.Request;
import com.dg.mtms.server.model.request.HttpRequest;
import com.dg.mtms.server.model.response.HttpResponse;
import com.dg.mtms.server.util.HttpParser;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

public class RequestDispatcher implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(RequestDispatcher.class);
    private final Map<String, Class<?>> controllers;
    private final Socket socket;
    private final ObjectMapper objectMapper;

    public RequestDispatcher(Map<String, Class<?>> controllers, Socket socket) {
        this.controllers = controllers;
        this.socket = socket;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void run() {
        try (
            BufferedReader socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter socketWriter = new PrintWriter(socket.getOutputStream(), true)
        ) {
            HttpParser parser = new HttpParser();
            HttpRequest httpRequest = parser.parse(socketReader);

            Optional<String> controllerBasePath = controllers.keySet()
                .stream()
                .filter(httpRequest.getEndpoint()::startsWith)
                .findFirst();
            if(controllerBasePath.isEmpty()) {
                socketWriter.write(HttpResponse.notFound().toString());
                throw new IllegalStateException("No controller found for " + httpRequest.getEndpoint());
            }

            String endpointPath = httpRequest.getEndpoint().substring(controllerBasePath.get().length());
            Optional<Method> matchedMethod = Arrays.stream(controllers.get(controllerBasePath.get()).getMethods())
                .filter(m ->
                    m.isAnnotationPresent(Request.class) &&
                    m.getDeclaredAnnotation(Request.class)
                        .endpoint()
                        .startsWith(endpointPath) &&
                    m.getDeclaredAnnotation(Request.class)
                        .method()
                        .equals(httpRequest.getMethod().name())
                )
                .findFirst();

            if(matchedMethod.isEmpty()) {
                socketWriter.write(HttpResponse.notFound().toString());
                throw new IllegalStateException("Endpoint not found");
            }



            HttpResponse response = (HttpResponse) matchedMethod.get().invoke(
                Singleton.getInstance(controllers.get(controllerBasePath.get())),
                objectMapper.readValue(httpRequest.getBody(), matchedMethod.get().getParameterTypes()[0])
            );
            socketWriter.write(response.toString());
        } catch (IOException | InvocationTargetException | IllegalAccessException | IllegalStateException e) {
            logger.error("Error while dispatching request!", e);
        } finally {
            try {
                socket.close();
            } catch (IOException e) {
                logger.error("Error while closing socket!", e);
            }
        }
    }
}
