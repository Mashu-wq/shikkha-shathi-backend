package com.runtimerebels.authservice.mappers;

import com.runtimerebels.authservice.dtos.UserRegistrationResponse;
import com.runtimerebels.authservice.models.User;
import com.shikkhasathi.dtos.UserRegistrationRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {

    @Mapping(target = "roles", ignore = true)
    User toUser(UserRegistrationRequest dto);

    @Mapping(source = "user.id", target = "userId")
    UserRegistrationResponse toUserRegistrationResponse(User user, String token);
}
