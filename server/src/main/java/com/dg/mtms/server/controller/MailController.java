package com.dg.mtms.server.controller;

import com.dg.mtms.server.Singleton;
import com.dg.mtms.server.annnotation.Controller;
import com.dg.mtms.server.annnotation.Request;
import com.dg.mtms.server.service.MailService;

@Controller(basePath = "/mail")
public class MailController extends Singleton<MailController> {
    private final MailService mailService;

    private MailController(MailService mailService) {
        this.mailService = mailService;
    }

    public static void createInstance(MailService mailService) {
        addInstance(new MailController(mailService));
    }

    public static MailController getInstance() {
        return Singleton.getInstance(MailController.class);
    }

    @Request(endpoint = "/send-package", method = "POST")
    public void sendPackage() {
        System.out.println("Sending package to server");
    }
}
