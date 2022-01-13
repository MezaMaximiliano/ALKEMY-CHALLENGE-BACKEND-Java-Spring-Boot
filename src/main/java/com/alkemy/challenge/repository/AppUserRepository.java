package com.alkemy.challenge.repository;

import com.alkemy.challenge.entity.AppUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface AppUserRepository extends JpaRepository<AppUser,Long> {

    AppUser findByEmail(String email);

    @Modifying
    @Query(value = "UPDATE app_user SET enable = false WHERE id = ?1",nativeQuery = true)
    void disableUser(Long id);

    @Modifying
    @Query(value = "UPDATE app_user SET enable = true WHERE id = ?1",nativeQuery = true)
    void enableUser(Long id);

    Optional<AppUser> findById(Long id);

}
