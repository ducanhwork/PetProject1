package dev.anhpd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.anhpd.entity.model.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, String> {}
