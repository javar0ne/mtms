package com.dg.mtms.client.manager.util;

import com.dg.mtms.common.response.HttpResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;

public class ParseBodyResponseState implements ResponseState {
    private static final Logger logger = LoggerFactory.getLogger(ParseBodyResponseState.class);

    @Override
    public ResponseState handle(BufferedReader reader, HttpResponse httpResponse) {
        try {
            int contentLength = Integer.parseInt(httpResponse.getHeaders().get("Content-Length"));
            char[] bodyChars = new char[contentLength];
            int totalRead = 0;
            while (totalRead < contentLength) {
                int read = reader.read(bodyChars, totalRead, contentLength - totalRead);
                if (read == -1) {
                    throw new IOException("Unexpected end of stream!");
                }
                totalRead += read;
            }
            httpResponse.setBody(new String(bodyChars));
        } catch (IOException e) {
            logger.error("Error while parsing body!", e);
        }
        return new DoneResponseState();
    }
}
