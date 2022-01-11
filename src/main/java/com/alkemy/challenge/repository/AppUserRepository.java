package com.alkemy.challenge.repository;

import com.alkemy.challenge.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AppUserRepository extends JpaRepository<AppUser,Long> {


    AppUser findByEmail(String email);
}
