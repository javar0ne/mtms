package com.dg.mtms.server.model.request;

import java.io.BufferedReader;

public class DoneRequestState implements RequestState {
    @Override
    public RequestState handle(BufferedReader reader, HttpRequest httpRequest) {
        return this;
    }
}
