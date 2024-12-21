package dev.anhpd.service;

import dev.anhpd.entity.dto.request.PermissionRequest;
import dev.anhpd.entity.dto.response.PermissionResponse;

import java.util.List;

public interface PermissionService {
    PermissionResponse create(PermissionRequest request);
    List<PermissionResponse> getAll();
    void delete(String name);
}
