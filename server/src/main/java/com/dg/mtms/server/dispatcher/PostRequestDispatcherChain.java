package com.dg.mtms.server.dispatcher;

public class PostRequestDispatcherChain extends RequestDispatcherChain {
    protected PostRequestDispatcherChain(RequestDispatcherChain nextChain, String httpMethod, String httpPath) {
        super(httpMethod, httpPath, nextChain);
    }

    @Override
    public boolean execute() {
        if(httpMethod.equals("POST")) {
            System.out.println("Handling POST request!");
        }

        return executeNext();
    }
}
