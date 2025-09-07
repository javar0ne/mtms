package com.dg.mtms.client.manager.util;


import com.dg.mtms.common.response.HttpResponse;

import java.io.BufferedReader;

public class DoneResponseState implements ResponseState {
    @Override
    public ResponseState handle(BufferedReader reader, HttpResponse httpResponse) {
        return this;
    }
}
