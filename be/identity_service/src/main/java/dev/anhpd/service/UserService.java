package dev.anhpd.service;

import java.util.List;
import java.util.UUID;

import dev.anhpd.entity.dto.request.UserCreateRequest;
import dev.anhpd.entity.dto.request.UserUpdateRequest;
import dev.anhpd.entity.dto.response.UserResponse;

public interface UserService {
    public List<UserResponse> getAllUsers();

    public UserResponse getUserById(UUID id);

    public UserResponse getUserByUsername(String username);

    public UserResponse createUser(UserCreateRequest user);

    public UserResponse updateUser(UUID uuid, UserUpdateRequest user) throws Exception;

    public void deleteUser(UUID id) throws Exception;

    UserResponse getMyInfo();
}
