package com.dg.mtms.server.model.request;

import java.io.BufferedReader;

public interface RequestState {
    RequestState handle(BufferedReader reader, HttpRequest httpRequest);
}
