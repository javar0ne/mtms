package com.dg.mtms.server.mapper;

import com.dg.mtms.server.model.MailPackage;
import com.dg.mtms.server.model.request.SendMailPackageRequest;
import com.dg.mtms.server.model.response.SendMailPackageResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MailPackageMapper {
    MailPackageMapper INSTANCE = Mappers.getMapper(MailPackageMapper.class);

    @Mappings({
        @Mapping(target = "id", ignore = true)
    })
    MailPackage toMailPackage(SendMailPackageRequest request);
    SendMailPackageResponse toSendMailPackageResponse(MailPackage mailPackage);
}
