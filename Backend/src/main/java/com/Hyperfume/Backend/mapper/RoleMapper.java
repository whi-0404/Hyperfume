package com.Hyperfume.Backend.mapper;

import org.mapstruct.Mapper;

import com.Hyperfume.Backend.dto.request.RoleRequest;
import com.Hyperfume.Backend.dto.response.RoleResponse;
import com.Hyperfume.Backend.entity.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
