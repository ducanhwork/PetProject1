package dev.anhpd.mapper;

import dev.anhpd.entity.dto.request.PermissionRequest;
import dev.anhpd.entity.dto.response.PermissionResponse;
import dev.anhpd.entity.model.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest request);
    PermissionResponse toPermissionResponse(Permission permission);
}
