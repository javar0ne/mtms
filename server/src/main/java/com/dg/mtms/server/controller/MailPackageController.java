package com.dg.mtms.server.controller;

import com.dg.mtms.server.Singleton;
import com.dg.mtms.server.annotation.Controller;
import com.dg.mtms.server.annotation.Request;
import com.dg.mtms.server.annotation.RequestBody;
import com.dg.mtms.server.annotation.RequestParam;
import com.dg.mtms.server.mapper.MailPackageMapper;
import com.dg.mtms.server.model.MailPackage;
import com.dg.mtms.server.model.request.SendMailPackageRequest;
import com.dg.mtms.server.model.response.HttpResponse;
import com.dg.mtms.server.model.response.SendMailPackageResponse;
import com.dg.mtms.server.service.MailPackageService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller(basePath = "/mail")
public class MailPackageController extends Singleton<MailPackageController> {
    private static final Logger logger = LoggerFactory.getLogger(MailPackageController.class);

    private final MailPackageService mailPackageService;
    private final ObjectMapper objectMapper;

    private MailPackageController(MailPackageService mailPackageService) {
        this.mailPackageService = mailPackageService;
        this.objectMapper = new ObjectMapper();
    }

    public static void createInstance(MailPackageService mailPackageService) {
        addInstance(new MailPackageController(mailPackageService));
    }
    @Request(endpoint = "/send-package", method = "POST")
    public HttpResponse sendPackage(@RequestBody SendMailPackageRequest sendMailPackageRequest) throws JsonProcessingException {
        logger.info("sending package: {}", sendMailPackageRequest);
        MailPackage mailPackage = mailPackageService.sendPackage(MailPackageMapper.INSTANCE.toMailPackage(sendMailPackageRequest));
        SendMailPackageResponse response = MailPackageMapper.INSTANCE.toSendMailPackageResponse(mailPackage);
        logger.info("sent package. id: {}", response.getId());
        return HttpResponse.ok(objectMapper.writeValueAsString(response));
    }
    @Request(endpoint = "/track-package")
    public HttpResponse trackPackage(@RequestParam(value = "numberPackage") String numberPackage) {
        logger.info("tracking package: {}",numberPackage);
        return HttpResponse.ok();
    }
}