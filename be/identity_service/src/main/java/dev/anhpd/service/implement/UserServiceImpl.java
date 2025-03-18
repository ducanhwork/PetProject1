package dev.anhpd.service.implement;

import static java.rmi.server.LogStream.log;
import static lombok.AccessLevel.PRIVATE;

import java.util.*;

import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import dev.anhpd.entity.dto.request.UserCreateRequest;
import dev.anhpd.entity.dto.request.UserUpdateRequest;
import dev.anhpd.entity.dto.response.UserResponse;
import dev.anhpd.entity.model.User;
import dev.anhpd.exception.AppException;
import dev.anhpd.exception.ErrorCode;
import dev.anhpd.mapper.UserMapper;
import dev.anhpd.repository.RoleRepository;
import dev.anhpd.repository.UserRepository;
import dev.anhpd.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@RequiredArgsConstructor
@FieldDefaults(level = PRIVATE, makeFinal = true)
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    RoleRepository roleRepository;
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    @Override
    //    @PreAuthorize("hasRole('ADMIN')")
    @PreAuthorize("hasAuthority('CREATE_POST')")
    public List<UserResponse> getAllUsers() {
        var auth = SecurityContextHolder.getContext().getAuthentication();
        auth.getAuthorities().forEach(System.out::println);
        List<User> listUser = userRepository.findAll();
        List<UserResponse> userResponses = new ArrayList<>();
        for (User user : listUser) {
            userResponses.add(userMapper.toUserResponse(user));
        }
        return userResponses;
    }

    @PostAuthorize("returnObject.username == authentication.name")
    @Override
    public UserResponse getUserById(UUID id) {
        User user = userRepository.findUserById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse getUserByUsername(String username) {
        return null;
    }

    @Override
    public UserResponse createUser(UserCreateRequest request) {
        log.info("Service: create user");
        User user = userMapper.fromCreatetoUser(request);
        user.setRoles(Set.of(roleRepository.findById("USER").orElseThrow()));
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);
        return userMapper.toUserResponse(user);
    }

    @Override
    public UserResponse updateUser(UUID uuid, UserUpdateRequest user) throws Exception {
        User us = userRepository.findUserById(uuid).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        us = userMapper.fromUpdatetoUser(user, us);
        us.setPassword(passwordEncoder.encode(us.getPassword()));
        var roles = roleRepository.findAllById(user.getRoles());
        us.setRoles(new HashSet<>(roles));
        userRepository.save(us);
        return userMapper.toUserResponse(us);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Override
    public void deleteUser(UUID id) throws Exception {
        User us = userRepository.findUserById(id).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_FOUND));
        userRepository.delete(us);
    }
}
