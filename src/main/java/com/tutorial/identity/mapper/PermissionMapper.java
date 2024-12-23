package com.tutorial.identity.mapper;

import com.tutorial.identity.dto.request.PermissionCreationRequest;
import com.tutorial.identity.dto.response.PermissionResponse;
import com.tutorial.identity.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionCreationRequest request);
    PermissionResponse toPermissionResponse(Permission permission);
}
