package dev.anhpd.service.implement;

import dev.anhpd.entity.dto.request.RoleRequest;
import dev.anhpd.entity.dto.response.RoleResponse;
import dev.anhpd.mapper.RoleMapper;
import dev.anhpd.repository.PermissionRepository;
import dev.anhpd.repository.RoleRepository;
import dev.anhpd.service.RoleService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleServiceImpl implements RoleService {
    RoleRepository roleRepository;
    RoleMapper roleMapper;
    PermissionRepository permissionRepository;


    @Override
    public RoleResponse create(RoleRequest request) {
        var role = roleMapper.toRole(request);
        // get all permissions by id
        var permissions = permissionRepository.findAllById(request.getPermissions());
        role.setPermissions(new HashSet<>(permissions));

        roleRepository.save(role);

        return roleMapper.toRoleResponse(role);
    }

    @Override
    public List<RoleResponse> getAll() {
        var roles = roleRepository.findAll();
        return roles.stream().map(roleMapper::toRoleResponse).toList();
    }

    @Override
    public void delete(String name) {
        roleRepository.deleteById(name);
    }
}
