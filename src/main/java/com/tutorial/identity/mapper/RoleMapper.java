package com.tutorial.identity.mapper;

import com.tutorial.identity.dto.request.PermissionCreationRequest;
import com.tutorial.identity.dto.request.RoleCreationRequest;
import com.tutorial.identity.dto.response.PermissionResponse;
import com.tutorial.identity.dto.response.RoleResponse;
import com.tutorial.identity.entity.Permission;
import com.tutorial.identity.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleCreationRequest request);
    RoleResponse toRoleResponse(Role role);
}
