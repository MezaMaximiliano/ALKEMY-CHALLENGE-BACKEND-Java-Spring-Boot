package com.alkemy.challenge.repository;

import com.alkemy.challenge.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RoleRepository extends JpaRepository<Role,Long> {

    Role findByName(String name);
    @Override
    List<Role> findAll();

}
