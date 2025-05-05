package com.dg.mtms.server.model.request;

import java.io.BufferedReader;
import java.io.IOException;

import static com.dg.mtms.server.enums.HttpMethod.POST;

public class ParseHeaderRequestState implements RequestState{
    @Override
    public RequestState handle(BufferedReader reader, HttpRequest httpRequest) {
        try {
            String line = reader.readLine();
            if(line.isEmpty()){
                if(POST.equals(httpRequest.getMethod())){
                    return new ParseBodyRequestState();
                } else {
                    return new DoneRequestState();
                }
            } else {
                String[] header = line.split(": ", 2);
                if (header.length == 2) {
                    httpRequest.getHeaders().put(header[0], header[1]);
                }
                return this;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
