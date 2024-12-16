package dev.anhpd.controller;

import dev.anhpd.entity.dto.request.UserUpdateRequest;
import dev.anhpd.exception.ErrorCode;
import dev.anhpd.entity.dto.request.UserCreateRequest;
import dev.anhpd.entity.dto.response.ApiResponse;
import dev.anhpd.entity.dto.response.UserResponse;
import dev.anhpd.service.UserServiceImpl;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static lombok.AccessLevel.PRIVATE;
@Slf4j
@RestController
@RequestMapping("/api/user/v1")
@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
public class UserController {
    UserServiceImpl userService;
    //Get All Users
    @GetMapping("/users")
    public ResponseEntity<ApiResponse> getAllUsers() {
        log.info("Get all users");
        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Username : {}", authentication.getName());
        log.info("Authorities : ", authentication.getAuthorities());
        List<UserResponse> listUser = userService.getAllUsers();
        return ResponseEntity.ok(ApiResponse.builder()
                .message(ErrorCode.USERS_FOUND.getMessage())
                .data(listUser)
                .code(ErrorCode.USERS_FOUND.getCode())
                .build()
        );
    }

    //Create a user
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> createUser(@RequestBody @Valid UserCreateRequest user) {
        UserResponse userResponse = userService.createUser(user);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("User created successfully")
                .data(userResponse)
                .code(200)
                .build()
        );
    }
    //Update user
    @PutMapping("/update/{user_id}")
    public ResponseEntity<ApiResponse> updateUser(@PathVariable("user_id") UUID uuid, @RequestBody @Valid UserUpdateRequest user) throws Exception {
        UserResponse userResponse = userService.updateUser(uuid,user);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("User updated successfully")
                .data(userResponse)
                .code(200)
                .build()
        );
    }
    //Delete user
    @DeleteMapping("/delete/{user_id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable("user_id") UUID id) throws Exception {
        userService.deleteUser(id);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("User deleted successfully")
                .code(200)
                .build()
        );
    }
    //Get user by id
    @GetMapping("/get/{user_id}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable("user_id") UUID id) {
        UserResponse userResponse = userService.getUserById(id);
        return ResponseEntity.ok(ApiResponse.builder()
                .message("User found successfully")
                .data(userResponse)
                .code(200)
                .build()
        );
    }


}
