package dev.danh.entities.dtos.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCreateRequest {
    @NotNull(message = "NULL_INPUT")
    String username;
    @NotNull(message = "NULL_INPUT")
    @Length(min = 8, message = "LENGTH_PASSWORD")
    String password;
    @NotNull(message = "NULL_INPUT")
    @Pattern(regexp = "^[A-Za-z0-9+_.-]+@(.+)$", message = "INVALID_EMAIL")
    String email;
    Boolean isActive = true;
}
