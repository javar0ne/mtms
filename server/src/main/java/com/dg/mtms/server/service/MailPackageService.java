package com.dg.mtms.server.service;

import com.dg.mtms.common.request.SendMailPackageRequest;
import com.dg.mtms.server.model.Singleton;
import com.dg.mtms.server.exception.EntityNotFoundException;
import com.dg.mtms.server.mapper.MailPackageMapper;
import com.dg.mtms.server.model.Dimension;
import com.dg.mtms.server.model.MailPackage;
import com.dg.mtms.server.model.User;
import com.dg.mtms.server.repository.MailPackageRepository;

import java.util.List;
import java.util.Optional;

public class MailPackageService extends Singleton<MailPackageService> {
    private final UserService userService;
    private final MailPackageRepository mailPackageRepository;

    private MailPackageService(UserService userService, MailPackageRepository mailPackageRepository) {
        this.userService = userService;
        this.mailPackageRepository = mailPackageRepository;
    }

    public static void createInstance(UserService userService, MailPackageRepository mailPackageRepository) {
        addInstance(new MailPackageService(userService, mailPackageRepository));
    }

    public static MailPackageService getInstance() {
        return Singleton.getInstance(MailPackageService.class);
    }

    public MailPackage save(SendMailPackageRequest request) {
        Optional<User> user = userService.findByUsername(request.username());
        if (user.isEmpty()) {
            throw new EntityNotFoundException("No user found with username: " + request.username());
        }

        return mailPackageRepository.save(MailPackageMapper.INSTANCE.toMailPackage(request, user.get().getId()));
    }

    public void updateStatus(Long id, String status) {
        mailPackageRepository.updateStatus(id, status);
    }

    public MailPackage trackPackage(Long packageNumber) {
        return mailPackageRepository.findById(packageNumber)
            .orElseThrow(() -> new EntityNotFoundException("No package found with id: " + packageNumber));
    }

    public Double findFee(Double weight, Dimension dimension) {
        return mailPackageRepository.findFee(weight, dimension)
            .orElseThrow(() -> new EntityNotFoundException("No fee found for package with weight: " + weight + " and dimension: " + dimension));
    }

    public List<MailPackage> findPackages(String username) {
        Optional<User> user = userService.findByUsername(username);
        if (user.isEmpty()) {
            throw new EntityNotFoundException("No user found with username: " + username);
        }

        return mailPackageRepository.findPackages(user.get().getId());
    }
}
