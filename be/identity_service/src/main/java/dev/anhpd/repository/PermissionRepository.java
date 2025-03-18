package dev.anhpd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import dev.anhpd.entity.model.Permission;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {}
