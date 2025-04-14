package com.dg.mtms.server.dispatcher;

public class GetRequestDispatcherChain extends RequestDispatcherChain {
    protected GetRequestDispatcherChain(RequestDispatcherChain nextChain, String httpMethod, String httpPath) {
        super(httpMethod, httpPath, nextChain);
    }

    @Override
    public boolean execute() {
        if(httpMethod.equals("GET")) {
            System.out.println("Handling GET request!");
        }

        return executeNext();
    }
}
