package com.dg.mtms.server.service;

import com.dg.mtms.server.Singleton;
import com.dg.mtms.server.repository.MailRepository;

public class MailService extends Singleton<MailService> {
    private final MailRepository mailRepository;

    private MailService(MailRepository mailRepository) {
        this.mailRepository = mailRepository;
    }

    public static void createInstance(MailRepository mailRepository) {
        addInstance(new MailService(mailRepository));
    }

    public static MailService getInstance() {
        return Singleton.getInstance(MailService.class);
    }
}
