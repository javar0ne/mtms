package com.dg.mtms.server.mapper;

import com.dg.mtms.server.model.User;
import com.dg.mtms.server.model.request.dto.UserCreateRequest;
import com.dg.mtms.server.model.response.dto.UserCreateResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mappings({
        @Mapping(target = "id", ignore = true)
    })
    User toUser(UserCreateRequest request);

    UserCreateResponse toUserCreateResponse(User user);
}
