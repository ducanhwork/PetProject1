package dev.anhpd.entity.model;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

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
    @Column(nullable = false, unique = true)
    private String username;
    private String password;
    @Column(unique = true)
    private String email;
    private LocalDate dob;
    @ManyToMany
    private Set<Role> roles;
    private boolean enabled = true;
}
