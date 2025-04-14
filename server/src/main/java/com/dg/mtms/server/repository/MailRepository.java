package com.dg.mtms.server.repository;

import com.dg.mtms.server.Singleton;

public class MailRepository extends Singleton<MailRepository> {
    private MailRepository() {}

    public static void createInstance() {
        addInstance(new MailRepository());
    }

    public static MailRepository getInstance() {
        return Singleton.getInstance(MailRepository.class);
    }
}
