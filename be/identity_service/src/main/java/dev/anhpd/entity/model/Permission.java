package dev.anhpd.entity.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Permission {
    @Id
    private String name;

    private String description;
}
