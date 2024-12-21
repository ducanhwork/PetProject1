package dev.anhpd.controller;

import dev.anhpd.entity.dto.request.RoleRequest;
import dev.anhpd.entity.dto.response.ApiResponse;
import dev.anhpd.service.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Slf4j
@FieldDefaults(makeFinal = true, level = lombok.AccessLevel.PRIVATE)
@RequiredArgsConstructor
@RequestMapping("/api/role/v1")
public class RoleController {
    RoleService roleService;

    @GetMapping()
    public ResponseEntity<ApiResponse> getAll() {
        var roles = roleService.getAll();
        return ResponseEntity.ok(ApiResponse.builder()
                .code(200)
                .data(roles)
                .message("Get all roles successfully")
                .build());
    }

    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody RoleRequest request) {
        log.info("Create role: {}", request);

        return ResponseEntity.ok(ApiResponse.builder()
                .code(200)
                .data(roleService.create(request))
                .message("Create role successfully")
                .build());
    }
}
