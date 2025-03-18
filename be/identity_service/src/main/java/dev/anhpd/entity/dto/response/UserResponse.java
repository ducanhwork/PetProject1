package dev.anhpd.entity.dto.response;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;

import dev.anhpd.entity.model.Role;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class UserResponse {
    private String user_id;
    private String fullname;
    private String username;
    private String password;
    private String email;
    private LocalDate dob;
    private Set<Role> role;
    private boolean enabled;
}
