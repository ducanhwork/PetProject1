package dev.anhpd.entity.dto.request;

import java.time.LocalDate;
import java.util.List;

import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import dev.anhpd.utility.DobConstraint;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateRequest {
    @NotNull(message = "NULL_INPUT")
    private String fullname;

    @NotNull(message = "NULL_INPUT")
    @Length(min = 5, max = 50, message = "LENGTH_USERNAME")
    private String username;

    @NotNull(message = "NULL_INPUT")
    @Length(min = 8, message = "LENGTH_PASSWORD")
    private String password;

    @Nullable
    @Pattern(regexp = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$", message = "EMAIL_FORMAT")
    private String email;

    @DobConstraint(message = "INVALID_DOB", min = 18)
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dob;

    private boolean enabled = true;
    private List<String> roles;
}
