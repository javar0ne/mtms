package com.dg.mtms.server.model.response.dto;

import com.dg.mtms.server.model.PackageStatus;

public record SendMailPackageResponse (
    Long id,
    String receiver,
    String address,
    Double weight,
    PackageStatus status,
    Long userId
) {}
