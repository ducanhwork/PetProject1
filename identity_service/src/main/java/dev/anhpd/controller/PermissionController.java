package dev.anhpd.controller;

import dev.anhpd.entity.dto.request.PermissionRequest;
import dev.anhpd.entity.dto.response.ApiResponse;
import dev.anhpd.entity.dto.response.PermissionResponse;
import dev.anhpd.service.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/permissions/v1")
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
@Slf4j
public class PermissionController {
    PermissionService permissionService;

    @PostMapping
    ApiResponse<PermissionResponse> create(@RequestBody PermissionRequest request) {
        log.info("Create permission: {}", request);
        var response = permissionService.create(request);
        return ApiResponse.<PermissionResponse>builder()
                .code(200)
                .data(response)
                .message("Create permission successfully")
                .build();
    }
    @GetMapping
    ApiResponse<List<PermissionResponse>> getAll() {
        log.info("Get all permissions");
        List<PermissionResponse> response = permissionService.getAll();
        return ApiResponse.<List<PermissionResponse>>builder()
                .code(200)
                .data(response)
                .message("Get all permissions successfully")
                .build();
    }
    @DeleteMapping("/{name}")
    ApiResponse<Void> delete(@PathVariable String name) {
        log.info("Delete permission: {}", name);
        permissionService.delete(name);
        return ApiResponse.<Void>builder()
                .code(200)
                .message("Delete permission successfully")
                .build();
    }
}
