package com.dg.mtms.server.model.request;

import java.io.BufferedReader;
import java.io.IOException;

public class ParseBodyRequestState implements RequestState {
    @Override
    public RequestState handle(BufferedReader reader, HttpRequest httpRequest) {
        try {
            int contentLength = Integer.parseInt(httpRequest.getHeaders().get("Content-Length"));
            char[] bodyChars = new char[contentLength];
            int totalRead = 0;
            while (totalRead < contentLength) {
                int read = reader.read(bodyChars, totalRead, contentLength - totalRead);
                if (read == -1) {
                    throw new IOException("Unexpected end of stream!");
                }
                totalRead += read;
            }
            httpRequest.setBody(new String(bodyChars));
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
        return new DoneRequestState();
    }
}
