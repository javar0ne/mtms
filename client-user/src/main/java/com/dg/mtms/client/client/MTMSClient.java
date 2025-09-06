package com.dg.mtms.client.client;

import com.dg.mtms.common.model.Dimension;
import com.dg.mtms.common.request.SendMailPackageRequest;
import com.dg.mtms.common.request.UserCreateRequest;
import com.dg.mtms.common.response.HttpResponse;

public class MTMSClient extends HttpClient {
    private static final String BASE_URL = "http://127.0.0.1:8080";
    private static final String CREATE_USER_PATH = "/v1/user";
    private static final String MAIL_SEND_PACKAGE_PATH = "/v1/mail/send-package";
    private static final String MAIL_TRACK_PACKAGE_PATH = "/v1/mail/track-package?packageNumber=%s";
    private static final String MAIL_CALCULATE_FEE_PATH = "/v1/mail/calculate-fee?length=%f&width=%f&height=%f";
    private static final String MAIL_USER_HISTORY_PATH = "/v1/mail/user?username=%s";

    private static final MTMSClient INSTANCE = new MTMSClient();


    private MTMSClient() {}

    public static MTMSClient getInstance() {
        return INSTANCE;
    }

    public HttpResponse createUser(UserCreateRequest userCreateRequest) {
        return executePostRequest(BASE_URL, CREATE_USER_PATH, userCreateRequest);
    }

    public HttpResponse sendPackage(SendMailPackageRequest sendMailPackageRequest) {
        return executePostRequest(BASE_URL, MAIL_SEND_PACKAGE_PATH, sendMailPackageRequest);
    }

    public HttpResponse trackPackage(String packageNumber) {
        return executeGetRequest(BASE_URL, String.format(MAIL_TRACK_PACKAGE_PATH, packageNumber));
    }

    public HttpResponse calculateFee(Dimension dimension) {
        return executeGetRequest(
            BASE_URL,
            String.format(
                MAIL_CALCULATE_FEE_PATH,
                dimension.getLength(),
                dimension.getWidth(),
                dimension.getHeight()
            )
        );
    }

    public HttpResponse getUserHistory(String username) {
        return executeGetRequest(BASE_URL, String.format(MAIL_USER_HISTORY_PATH, username));
    }
}
