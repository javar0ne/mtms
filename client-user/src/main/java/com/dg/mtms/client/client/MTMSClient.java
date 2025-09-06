package com.dg.mtms.client.client;

import com.dg.mtms.client.client.request.HttpRequest;
import com.dg.mtms.common.request.SendMailPackageRequest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.dg.mtms.common.model.Dimension;
import com.dg.mtms.common.request.UserCreateRequest;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class MTMSClient {
    private static final String BASE_URL = "http://127.0.0.1:8080";
    private static final String CREATE_USER_PATH = "/v1/user";
    private static final String MAIL_SEND_PACKAGE_PATH = "/v1/mail/send-package";
    private static final String MAIL_TRACK_PACKAGE_PATH = "/v1/mail/track-package?packageNumber=%s";
    private static final String MAIL_CALCULATE_FEE_PATH = "/v1/mail/calculate-fee?length=%f&width=%f&height=%f";
    private static final String MAIL_USER_HISTORY_PATH = "/v1/mail/user?username=%s";
    private static final MTMSClient INSTANCE = new MTMSClient();

    private final ObjectMapper objectMapper = new ObjectMapper();

    private MTMSClient() {}

    public static MTMSClient getInstance() {
        return INSTANCE;
    }

    private void executePostRequest(String path, Object content) {
        try(
            Socket socket = new Socket("127.0.0.1", 8080);
            PrintWriter socketWriter = new PrintWriter(socket.getOutputStream(), true)
        ) {
            String body = objectMapper.writeValueAsString(content);
            HttpRequest request = new HttpRequest();
            request.setMethod("POST");
            request.setPath(path);
            request.setHost(BASE_URL);
            request.setVersion("HTTP/1.1");
            request.addHeader("Content-Type", "application/json");
            request.addHeader("Content-Length", String.valueOf(body.length()));
            request.setBody(body);
            socketWriter.write(request.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void executeGetRequest(String path) {
        try(
            Socket socket = new Socket("127.0.0.1", 8080);
            PrintWriter socketWriter = new PrintWriter(socket.getOutputStream(), true)
        ) {
            HttpRequest request = new HttpRequest();
            request.setMethod("GET");
            request.setPath(path);
            request.setHost(BASE_URL);
            request.setVersion("HTTP/1.1");
            socketWriter.write(request.toString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void createUser(UserCreateRequest userCreateRequest) {
        executePostRequest(CREATE_USER_PATH, userCreateRequest);
    }

    public void sendPackage(SendMailPackageRequest sendMailPackageRequest) {
        executePostRequest(MAIL_SEND_PACKAGE_PATH, sendMailPackageRequest);
    }

    public void trackPackage(String packageNumber) {
        executeGetRequest(String.format(MAIL_TRACK_PACKAGE_PATH, packageNumber));
    }

    public void calculateFee(Dimension dimension) {
        executeGetRequest(
            String.format(
                MAIL_CALCULATE_FEE_PATH,
                dimension.getLength(),
                dimension.getWidth(),
                dimension.getHeight()
            )
        );
    }

    public void getUserHistory(String username) {
        executeGetRequest(String.format(MAIL_USER_HISTORY_PATH, username));
    }
}
