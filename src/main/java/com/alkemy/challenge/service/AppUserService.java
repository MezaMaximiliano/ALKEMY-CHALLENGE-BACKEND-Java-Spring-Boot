package com.alkemy.challenge.service;

import com.alkemy.challenge.entity.AppUser;
import com.alkemy.challenge.entity.Role;

import java.util.List;
import java.util.Optional;

public interface AppUserService {

    AppUser saveUser(AppUser appUser);
    Role saveRole (Role role);
    void addRoleToUser(String email, String rolName);
    AppUser getAppUser(String email);
    List<AppUser> getAppUsers();
    List<Role> findAllRoles();
    void disableUser(Long id);
    void enableUser(Long id);
    Optional<AppUser> findById(Long id);

}
