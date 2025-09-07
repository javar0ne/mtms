package com.dg.mtms.client.manager.client;

import com.dg.mtms.common.request.UpdatePackageStatusRequest;
import com.dg.mtms.common.response.HttpResponse;

public class MTMSClient extends HttpClient {
    private static final String BASE_URL = "http://127.0.0.1:8080";
    private static final String MAIL_UPDATE_STATUS_PATH = "/v1/mail/status";

    private static final MTMSClient INSTANCE = new MTMSClient();


    private MTMSClient() {}

    public static MTMSClient getInstance() {
        return INSTANCE;
    }

    public HttpResponse updatePackageStatus(UpdatePackageStatusRequest request) {
        return executePatchRequest(BASE_URL, MAIL_UPDATE_STATUS_PATH, request);
    }
}
