package com.alkemy.challenge.service;

import com.alkemy.challenge.entity.AppUser;
import com.alkemy.challenge.entity.Role;

import java.util.List;

public interface AppUserService {

    AppUser saveUser(AppUser appUser);
    Role saveRole (Role role);
    void addRoleToUser(String email, String rolName);
    AppUser getAppUser(String email);
    List<AppUser> getAppUsers();
    List<Role> findAllRoles();

}
