package dev.anhpd.entity.dto.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import dev.anhpd.entity.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;
@JsonInclude(JsonInclude.Include.NON_NULL)
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserResponse {
    private UUID user_id;
    private String fullname;
    private String username;
    private String password;
    private String email;
    private LocalDate dob;
    private Set<Role> role;
    private boolean enabled;
}
