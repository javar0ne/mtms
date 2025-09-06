package com.dg.mtms.client.util;

import com.dg.mtms.common.response.HttpResponse;

import java.io.BufferedReader;

public class HttpParser {
    public HttpResponse parse(BufferedReader reader) {
        HttpResponse request = new HttpResponse();
        ResponseState state = new ParseLineResponseState();

        while (!(state instanceof DoneResponseState)) {
            state = state.handle(reader, request);
        }
        return request;
    }
}
