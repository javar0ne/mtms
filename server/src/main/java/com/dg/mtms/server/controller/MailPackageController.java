package com.dg.mtms.server.controller;

import com.dg.mtms.common.request.SendMailPackageRequest;
import com.dg.mtms.server.model.Singleton;
import com.dg.mtms.server.annotation.Controller;
import com.dg.mtms.server.annotation.Request;
import com.dg.mtms.server.annotation.RequestBody;
import com.dg.mtms.server.annotation.RequestParam;
import com.dg.mtms.server.exception.EntityNotFoundException;
import com.dg.mtms.server.mapper.MailPackageMapper;
import com.dg.mtms.server.model.Dimension;
import com.dg.mtms.server.model.MailPackage;
import com.dg.mtms.common.response.HttpResponse;
import com.dg.mtms.server.service.MailPackageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.dg.mtms.common.request.UpdatePackageStatusRequest;
import com.dg.mtms.common.response.PackageFeeResponse;

import java.util.List;

@Controller(basePath = "/v1/mail")
public class MailPackageController extends Singleton<MailPackageController> {
    private static final Logger logger = LoggerFactory.getLogger(MailPackageController.class);

    private final MailPackageService mailPackageService;

    private MailPackageController(MailPackageService mailPackageService) {
        this.mailPackageService = mailPackageService;
    }

    public static void createInstance(MailPackageService mailPackageService) {
        addInstance(new MailPackageController(mailPackageService));
    }
    @Request(endpoint = "/send-package", method = "POST")
    public HttpResponse sendPackage(@RequestBody SendMailPackageRequest sendMailPackageRequest) {
        logger.info("sending package: {}", sendMailPackageRequest);
        try {
            MailPackage mailPackage = mailPackageService.save(sendMailPackageRequest);
            logger.info("sent package with id: {}", mailPackage.getId());
            return HttpResponse.ok(MailPackageMapper.INSTANCE.toSendMailPackageResponse(mailPackage));
        } catch (EntityNotFoundException e) {
            logger.error("error while sending package!", e);
            return HttpResponse.internalServerError();
        }
    }
    @Request(endpoint = "/track-package")
    public HttpResponse trackPackage(@RequestParam(value = "packageNumber") Long packageNumber) {
        logger.info("tracking package: {}", packageNumber);
        try {
            MailPackage mailPackage = mailPackageService.trackPackage(packageNumber);
            logger.info("found package: {}", mailPackage);
            return HttpResponse.ok(MailPackageMapper.INSTANCE.toSendMailPackageResponse(mailPackage));
        } catch (EntityNotFoundException e) {
            logger.error("error while tracking package!", e);
            return HttpResponse.internalServerError();
        }
    }

    @Request(endpoint = "/status", method = "PATCH")
    public HttpResponse updatePackageStatus(@RequestBody UpdatePackageStatusRequest request) {
        logger.info("updating package with id: {} to status: {}", request.id(), request.status());
        try {
            mailPackageService.updateStatus(request.id(), request.status());
            logger.info("package status updated!");
            return HttpResponse.noContent();
        } catch (EntityNotFoundException e) {
            logger.error("error while updating package status!", e);
            return HttpResponse.internalServerError();
        }
    }

    @Request(endpoint = "/calculate-fee")
    public HttpResponse calculateFee(
        @RequestParam(value = "length") Double length,
        @RequestParam(value = "width") Double width,
        @RequestParam(value = "height") Double height,
        @RequestParam(value = "weight") Double weight
    ) {
        logger.info("calculating fee for package with weight: {} and dimension: {} - {} - {}", weight, length, width, height);
        try {
            Dimension dimension = new Dimension(length, width, height);
            Double fee = mailPackageService.findFee(weight, dimension);
            logger.info("package fee found: {}", fee);
            return HttpResponse.ok(new PackageFeeResponse(fee));
        } catch (EntityNotFoundException e) {
            logger.error("error while looking for package fee!", e);
            return HttpResponse.internalServerError();
        }
    }

    @Request(endpoint = "/user")
    public HttpResponse pastPackages(@RequestParam(value = "username") String username) {
        logger.info("looking for past shipments for user: {}", username);
        try {
            List<MailPackage> mailPackages = mailPackageService.findPackages(username);
            logger.info("found {} past shipments for user: {}", mailPackages.size(), username);
            return HttpResponse.ok(
                mailPackages.stream()
                    .map(MailPackageMapper.INSTANCE::toSendMailPackageResponse)
                    .toList()
            );
        } catch (EntityNotFoundException e) {
            logger.error("error while looking for package fee!", e);
            return HttpResponse.internalServerError();
        }
    }
}