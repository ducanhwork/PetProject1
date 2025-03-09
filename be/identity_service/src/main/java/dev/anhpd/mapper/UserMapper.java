package dev.anhpd.mapper;

import dev.anhpd.entity.dto.request.UserCreateRequest;
import dev.anhpd.entity.dto.request.UserUpdateRequest;
import dev.anhpd.entity.dto.response.UserResponse;
import dev.anhpd.entity.model.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User fromCreatetoUser(UserCreateRequest userCreateRequest);
    @Mapping(target = "roles", ignore = true)
    User fromUpdatetoUser(UserUpdateRequest userUpdateRequest, @MappingTarget User user);
    UserResponse toUserResponse(User user);
}