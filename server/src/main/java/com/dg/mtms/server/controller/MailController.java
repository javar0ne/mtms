package com.dg.mtms.server.controller;

import com.dg.mtms.server.Singleton;
import com.dg.mtms.server.annnotation.Controller;
import com.dg.mtms.server.annnotation.Request;
import com.dg.mtms.server.service.MailService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller(basePath = "/mail")
public class MailController extends Singleton<MailController> {
    private final static Logger logger = LoggerFactory.getLogger(MailController.class);
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
        logger.info("Sending package to server");
    }
}
