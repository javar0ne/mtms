package com.dg.mtms.client.manager.util;

import com.dg.mtms.common.response.HttpResponse;

import java.io.BufferedReader;

public interface ResponseState {
    ResponseState handle(BufferedReader reader, HttpResponse httpResponse);
}
