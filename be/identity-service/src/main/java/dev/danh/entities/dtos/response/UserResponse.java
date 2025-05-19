package dev.danh.entities.dtos.response;


import dev.danh.entities.models.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;
import java.util.UUID;
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    UUID id;
    String username;
    String password;
    String email;
    Set<Role> roles;
    Boolean isActive;
}
