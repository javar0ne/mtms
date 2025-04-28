package com.dg.mtms.server.util;

import com.dg.mtms.server.model.request.DoneRequestState;
import com.dg.mtms.server.model.request.HttpRequest;
import com.dg.mtms.server.model.request.ParseLineRequestState;
import com.dg.mtms.server.model.request.RequestState;

import java.io.BufferedReader;

public class HttpParser {
    public HttpRequest parse(BufferedReader reader) {
        HttpRequest request = new HttpRequest();
        RequestState state = new ParseLineRequestState();

        while (!(state instanceof DoneRequestState)) {
            state = state.handle(reader, request);
        }
        return request;
    }
}
