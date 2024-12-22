package com.tutorial.identity.mapper;

import com.tutorial.identity.dto.request.UserCreationRequest;
import com.tutorial.identity.dto.request.UserUpdateRequest;
import com.tutorial.identity.dto.response.UserResponse;
import com.tutorial.identity.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserCreationRequest request);

    UserResponse toUserResponse(User user);

    void updateUser(@MappingTarget User user, UserUpdateRequest request);
}
