package dev.anhpd.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import dev.anhpd.entity.dto.request.RoleRequest;
import dev.anhpd.entity.dto.response.RoleResponse;
import dev.anhpd.entity.model.Role;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest request);

    RoleResponse toRoleResponse(Role role);
}
