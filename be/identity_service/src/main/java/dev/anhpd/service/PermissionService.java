package dev.anhpd.service;

import java.util.List;

import dev.anhpd.entity.dto.request.PermissionRequest;
import dev.anhpd.entity.dto.response.PermissionResponse;

public interface PermissionService {
    PermissionResponse create(PermissionRequest request);

    List<PermissionResponse> getAll();

    void delete(String name);
}
