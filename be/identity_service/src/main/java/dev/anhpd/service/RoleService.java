package dev.anhpd.service;

import dev.anhpd.entity.dto.request.RoleRequest;
import dev.anhpd.entity.dto.response.RoleResponse;

import java.util.List;

public interface RoleService {
   RoleResponse create(RoleRequest request);
   List<RoleResponse> getAll();
   void delete(String name);
}
