package com.dg.mtms.client.util;

import com.dg.mtms.common.response.HttpResponse;

import java.io.BufferedReader;
import java.io.IOException;

public class ParseHeaderResponseState implements ResponseState {
    @Override
    public ResponseState handle(BufferedReader reader, HttpResponse httpResponse) {
        try {
            String line = reader.readLine();
            if(line.isEmpty()) {
                String contentLength = httpResponse.getHeaders().get("Content-Length");
                if(contentLength != null && Integer.parseInt(contentLength) > 0) {
                    return new ParseBodyResponseState();
                } else {
                    return new DoneResponseState();
                }
            } else {
                String[] header = line.split(": ", 2);
                if (header.length == 2) {
                    httpResponse.getHeaders().put(header[0], header[1]);
                }
                return this;
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
