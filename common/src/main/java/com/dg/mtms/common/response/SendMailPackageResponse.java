package com.dg.mtms.common.response;


import com.dg.mtms.common.model.PackageStatus;

public record SendMailPackageResponse (
    Long id,
    String receiver,
    String address,
    Double weight,
    PackageStatus status,
    Long userId
) {}
