package com.dg.mtms.server.model.request;

import java.io.BufferedReader;
import java.io.IOException;

public class ParseLineRequestState implements RequestState{
    @Override
    public RequestState handle(BufferedReader bufferedReader, HttpRequest httpRequest) {
        try {
            String line = bufferedReader.readLine();
            String[] request = line.split(" ");
            httpRequest.setMethod(request[0]);
            httpRequest.setEndpoint(request[1]);
            httpRequest.setVersion(request[2]);
            return new ParseHeaderRequestState();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
