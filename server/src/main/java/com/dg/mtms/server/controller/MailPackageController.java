package com.dg.mtms.server.controller;

import com.dg.mtms.server.Singleton;
import com.dg.mtms.server.annotation.Controller;
import com.dg.mtms.server.annotation.Request;
import com.dg.mtms.server.annotation.RequestBody;
import com.dg.mtms.server.annotation.RequestParam;
import com.dg.mtms.server.exception.EntityNotFoundException;
import com.dg.mtms.server.mapper.MailPackageMapper;
import com.dg.mtms.server.model.MailPackage;
import com.dg.mtms.server.model.request.dto.UpdatePackageStatusRequest;
import com.dg.mtms.server.model.request.dto.SendMailPackageRequest;
import com.dg.mtms.server.model.response.HttpResponse;
import com.dg.mtms.server.model.response.dto.PackageFeeResponse;
import com.dg.mtms.server.service.MailPackageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    @Request(endpoint = "/find-fee")
    public HttpResponse findFee(@RequestParam(value = "weight") Double weight, @RequestParam(value = "dim") Double dimension) {
        logger.info("calculating fee for package with weight: {} and dimension: {}", weight, dimension);
        try {
            Double fee = mailPackageService.findFee(weight, dimension);
            logger.info("package fee found: {}", fee);
            return HttpResponse.ok(new PackageFeeResponse(fee));
        } catch (EntityNotFoundException e) {
            logger.error("error while looking for package fee!", e);
            return HttpResponse.internalServerError();
        }
    }

    @Request(endpoint = "/user")
    public HttpResponse pastPackages(@RequestParam(value = "userId") Long userId) {
        logger.info("looking for past shipments for user with id: {}", userId);
        try {
            List<MailPackage> mailPackages = mailPackageService.findByUserId(userId);
            logger.info("found {} past shipments for user with id: {}", mailPackages.size(), userId);
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