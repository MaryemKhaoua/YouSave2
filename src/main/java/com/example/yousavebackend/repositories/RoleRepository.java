package com.example.yousavebackend.repositories;

import com.example.yousavebackend.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
        Optional<Role> findByName(String name);
}
