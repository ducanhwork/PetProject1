package dev.danh.services.user.impl;

import dev.danh.entities.dtos.request.UserCreateRequest;
import dev.danh.entities.dtos.request.UserUpdateRequest;
import dev.danh.entities.dtos.response.UserResponse;
import dev.danh.entities.models.User;
import dev.danh.enums.ErrorCode;
import dev.danh.exception.AppException;
import dev.danh.mapper.UserMapper;
import dev.danh.repositories.user.UserRepository;
import dev.danh.services.user.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    @Override
    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(userMapper::toUserResponse)
                .toList();
    }

    @Override
    public UserResponse createUser(UserCreateRequest userCreateRequest) {
        User user = new User();
        user = userMapper.toUser(userCreateRequest, user);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        try{
            userRepository.save(user);
        }catch (DataIntegrityViolationException e)
        {
            throw new AppException(ErrorCode.USERNAME_OR_EMAIL_ALREADY_EXISTS);
        }
        return userMapper.toUserResponse(user);

    }

    @Override
    public UserResponse updateUser(UUID id, UserUpdateRequest userUpdateRequest) {
        User us = userRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        us = userMapper.toUser(userUpdateRequest, us);
        us.setPassword(passwordEncoder.encode(us.getPassword()));
        userRepository.save(us);
        return userMapper.toUserResponse(us);
    }

    @Override
    public void deleteUser(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        user.setIsActive(false);
        userRepository.save(user);
        log.info("User with id {} deleted", userId);
    }

    @Override
    public UserResponse getUserById(UUID userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse getUserByUsername(String username) {
        return null;
    }

    @Override
    public UserResponse getUserByEmail(String email) {
        return null;
    }

    @Override
    public UserResponse getMyProfile() {
        // Assuming you have a method to get the current user's ID
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User user = userRepository.findByUsername(username).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return userMapper.toUserResponse(user);
    }


}
