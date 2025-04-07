package server;

import server.annnotation.GetRequest;

public class Controller {

    @GetRequest(endpoint = "/send-package")
    public void sendPackage() {
        System.out.println("Sending package to server");
    }
}
