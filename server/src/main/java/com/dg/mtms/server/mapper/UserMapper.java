package com.dg.mtms.server.mapper;

import com.dg.mtms.server.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import com.dg.mtms.common.request.UserCreateRequest;
import com.dg.mtms.common.response.UserCreateResponse;

@Mapper
public interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mappings({
        @Mapping(target = "id", ignore = true)
    })
    User toUser(UserCreateRequest request);

    UserCreateResponse toUserCreateResponse(User user);
}
