package com.alkemy.challenge.controller;


import com.alkemy.challenge.entity.Role;
import com.alkemy.challenge.model.RoleToUserForm;
import com.alkemy.challenge.service.AppUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/roles")
public class RoleController {

    private final AppUserService userService;

    @GetMapping
    public ResponseEntity<List<Role>> getRoles(){
        return ResponseEntity.ok().body(userService.findAllRoles());
    }

    @PostMapping("/role/save")
    public ResponseEntity<Role> saveRole(@RequestBody Role role){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/auth/role/save").toUriString());
        return ResponseEntity.created(uri).body(userService.saveRole(role));
    }

    @PostMapping("/role/addtouser")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleToUserForm form){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/auth/role/save").toUriString());
        userService.addRoleToUser( form.getUserName(),form.getRoleName());
        return ResponseEntity.ok().build();
    }
}
