package com.dg.mtms.client.client;

import com.dg.mtms.client.client.request.HttpRequest;
import com.dg.mtms.client.util.DoneResponseState;
import com.dg.mtms.client.util.ParseLineResponseState;
import com.dg.mtms.client.util.ResponseState;
import com.dg.mtms.common.http.HttpMethod;
import com.dg.mtms.common.response.HttpResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.net.Socket;
import java.util.logging.Logger;

public abstract class HttpClient {
    private static final Logger logger = Logger.getLogger(HttpClient.class.getName());
    private final ObjectMapper objectMapper = new ObjectMapper();

    protected HttpResponse executePostRequest(String host, String path, Object content) {
        try (
            Socket socket = new Socket("127.0.0.1", 8080);
            PrintWriter socketWriter = new PrintWriter(socket.getOutputStream(), true)
        ) {
            String body = objectMapper.writeValueAsString(content);
            HttpRequest request = new HttpRequest();
            request.setMethod(HttpMethod.POST.name());
            request.setPath(path);
            request.setHost(host);
            request.setVersion("HTTP/1.1");
            request.addHeader("Content-Type", "application/json");
            request.addHeader("Content-Length", String.valueOf(body.length()));
            request.setBody(body);
            socketWriter.write(request.toString());
            socketWriter.flush();
            return readResponse(socket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    protected HttpResponse executeGetRequest(String host, String path) {
        try (
            Socket socket = new Socket("127.0.0.1", 8080);
            PrintWriter socketWriter = new PrintWriter(socket.getOutputStream(), true)
        ) {
            HttpRequest request = new HttpRequest();
            request.setMethod(HttpMethod.GET.name());
            request.setPath(path);
            request.setHost(host);
            request.setVersion("HTTP/1.1");
            socketWriter.write(request.toString());
            socketWriter.flush();
            return readResponse(socket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private HttpResponse readResponse(Socket socket) {
        InputStream inputStream;
        try {
            inputStream = socket.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

            HttpResponse response = new HttpResponse();
            ResponseState state = new ParseLineResponseState();

            while (!(state instanceof DoneResponseState)) {
                state = state.handle(reader, response);
            }
            return response;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }
}
