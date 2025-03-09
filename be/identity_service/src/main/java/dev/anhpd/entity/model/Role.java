package dev.anhpd.entity.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Entity
@Setter
@Getter
public class Role {
    @Id
    private String name;
    private String description;
    //many to many to permission
    @ManyToMany
    private Set<Permission> permissions;
}
