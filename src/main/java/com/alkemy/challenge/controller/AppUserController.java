package com.alkemy.challenge.controller;

import com.alkemy.challenge.entity.AppUser;
import com.alkemy.challenge.service.AppUserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class AppUserController {

    private final AppUserService userService;


    @GetMapping
    public ResponseEntity<List<AppUser>> getUsers(){
        return ResponseEntity.ok().body(userService.getAppUsers());
    }

    @PostMapping("/disable/{id}")
    public String disableAppUser(@PathVariable Long id,HttpServletResponse response){
        try {
            userService.disableUser(id);
            return "Usuario ha sido deshabilitado";
        }catch (Exception err){
            response.setStatus(400);
            return "Usuario no encontrado";
        }

    }

    @PostMapping("/enable/{id}")
    public String enableAppUser(@PathVariable Long id,HttpServletResponse response){
        try {
            userService.enableUser(id);
            return "Usuario ha sido habilitado";
        }catch (Exception err){
            response.setStatus(400);
            return "Usuario no encontrado";
        }

    }

    @GetMapping("/{id}")
    public Optional<AppUser> findById(@PathVariable Long id,HttpServletResponse response){
       if(userService.findById(id).isPresent()){
           return userService.findById(id);
       }else{
           response.setStatus(400);
           response.addHeader("Error","Usuario no encontrado");
           return null;
       }
    }


}

