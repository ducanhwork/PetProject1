package dev.anhpd.entity.model;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Data
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String fullname;

    @Column(nullable = false, unique = true, columnDefinition = ("VARCHAR(255) COLLATE utf8mb4_unicode_ci"))
    private String username;

    private String password;

    @Column(unique = true)
    private String email;

    private LocalDate dob;

    @ManyToMany
    private Set<Role> roles;

    private boolean enabled = true;
}
