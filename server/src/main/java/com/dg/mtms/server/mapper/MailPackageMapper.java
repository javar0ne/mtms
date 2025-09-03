package com.dg.mtms.server.mapper;

import com.dg.mtms.server.model.MailPackage;
import com.dg.mtms.server.model.request.dto.SendMailPackageRequest;
import com.dg.mtms.server.model.response.dto.SendMailPackageResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MailPackageMapper {
    MailPackageMapper INSTANCE = Mappers.getMapper(MailPackageMapper.class);

    @Mappings({
        @Mapping(target = "id", ignore = true),
        @Mapping(target = "userId", source = "userId"),
        @Mapping(target = "status", constant = "SHIPPED")
    })
    MailPackage toMailPackage(SendMailPackageRequest request, Long userId);
    SendMailPackageResponse toSendMailPackageResponse(MailPackage mailPackage);
}
