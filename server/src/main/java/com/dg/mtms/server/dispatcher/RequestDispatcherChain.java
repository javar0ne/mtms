package com.dg.mtms.server.dispatcher;

public abstract class RequestDispatcherChain {
    protected final String httpMethod;
    protected final String httpPath;
    protected final RequestDispatcherChain nextChain;

    protected RequestDispatcherChain(String httpMethod, String httpPath, RequestDispatcherChain nextChain) {
        this.httpMethod = httpMethod;
        this.httpPath = httpPath;
        this.nextChain = nextChain;
    }

    public abstract boolean execute();
    public boolean executeNext() {
        if (nextChain == null) return true;
        return nextChain.execute();
    }
}
