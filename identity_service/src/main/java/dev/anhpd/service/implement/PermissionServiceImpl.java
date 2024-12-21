package dev.anhpd.service.implement;

import dev.anhpd.entity.dto.request.PermissionRequest;
import dev.anhpd.entity.dto.response.PermissionResponse;
import dev.anhpd.entity.model.Permission;
import dev.anhpd.mapper.PermissionMapper;
import dev.anhpd.repository.PermissionRepository;
import dev.anhpd.service.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Service
public class PermissionServiceImpl implements PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    @Override
    public PermissionResponse create(PermissionRequest request) {
        System.out.println("Create permission: " + request);
        Permission permission = permissionMapper.toPermission(request);
        permission = permissionRepository.save(permission);
        return permissionMapper.toPermissionResponse(permission);
    }

    @Override
    public List<PermissionResponse> getAll() {
        var permissions = permissionRepository.findAll();
        return permissions.stream().map(permissionMapper::toPermissionResponse).toList();
    }

    @Override
    public void delete(String name) {
        permissionRepository.deleteById(name);
    }


}
