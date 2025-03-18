package dev.anhpd.service;

import java.util.List;

import dev.anhpd.entity.dto.request.RoleRequest;
import dev.anhpd.entity.dto.response.RoleResponse;

public interface RoleService {
    RoleResponse create(RoleRequest request);

    List<RoleResponse> getAll();

    void delete(String name);
}
