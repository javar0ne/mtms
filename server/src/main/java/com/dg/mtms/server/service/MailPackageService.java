package com.dg.mtms.server.service;

import com.dg.mtms.server.Singleton;
import com.dg.mtms.server.model.MailPackage;
import com.dg.mtms.server.repository.MailPackageRepository;

public class MailPackageService extends Singleton<MailPackageService> {
    private final MailPackageRepository mailPackageRepository;

    private MailPackageService(MailPackageRepository mailPackageRepository) {
        this.mailPackageRepository = mailPackageRepository;
    }

    public static void createInstance(MailPackageRepository mailPackageRepository) {
        addInstance(new MailPackageService(mailPackageRepository));
    }

    public static MailPackageService getInstance() {
        return Singleton.getInstance(MailPackageService.class);
    }

    public MailPackage sendPackage(MailPackage mailPackage) {
        return mailPackageRepository.insertPackage(mailPackage);
    }
}
