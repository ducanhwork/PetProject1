package dev.danh.services.user;

import dev.danh.entities.dtos.request.UserCreateRequest;
import dev.danh.entities.dtos.request.UserUpdateRequest;
import dev.danh.entities.dtos.response.UserResponse;

import java.util.List;
import java.util.UUID;

public interface UserService {
    List<UserResponse> getAllUsers();
    UserResponse createUser(UserCreateRequest userCreateRequest);
    UserResponse updateUser(UUID id,UserUpdateRequest userUpdateRequest);
    void deleteUser(UUID userId);
    UserResponse getUserById(UUID userId);
    UserResponse getUserByUsername(String username);
    UserResponse getUserByEmail(String email);

    UserResponse getMyProfile();
}
