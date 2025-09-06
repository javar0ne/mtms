package com.dg.mtms.client.util;

import com.dg.mtms.common.response.HttpResponse;
import com.dg.mtms.common.response.StatusCode;

import java.io.BufferedReader;
import java.io.IOException;

public class ParseLineResponseState implements ResponseState {
    @Override
    public ResponseState handle(BufferedReader bufferedReader, HttpResponse httpResponse) {
        try {
            String line = bufferedReader.readLine();
            String[] request = line.split(" ");

            httpResponse.setVersion(request[0]);
            httpResponse.setStatusCode(StatusCode.valueOf(Integer.parseInt(request[1])));

            return new ParseHeaderResponseState();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
