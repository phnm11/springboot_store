package com.nam.storespring.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import com.nam.storespring.dto.request.UserCreationRequest;
import com.nam.storespring.dto.request.UserUpdateRequest;
import com.nam.storespring.dto.response.UserResponse;
import com.nam.storespring.entity.User;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "role", ignore = true)
    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
