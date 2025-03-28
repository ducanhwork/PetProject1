package dev.anhpd.entity.model;

import java.util.Set;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Role {
    @Id
    private String name;

    private String description;
    // many to many to permission
    @ManyToMany
    private Set<Permission> permissions;
}
