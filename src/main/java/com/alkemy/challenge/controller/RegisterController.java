package com.alkemy.challenge.controller;

import com.alkemy.challenge.entity.AppUser;
import com.alkemy.challenge.entity.Role;
import com.alkemy.challenge.service.AppUserService;
import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URI;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class RegisterController {

    private final AppUserService userService;


    @PostMapping("/register")
    public ResponseEntity<AppUser> saveUser(@RequestBody AppUser user, HttpServletResponse response){
        URI uri = URI.create(ServletUriComponentsBuilder.fromCurrentContextPath().path("/auth/save").toUriString());
        AppUser appUser = userService.saveUser(user);

        if(appUser == null){
            response.addHeader("Error", "El usuario ya existe");
            return ResponseEntity.status(BAD_REQUEST).body(user);
        }else{
            return ResponseEntity.created(uri).body(appUser);
        }
    }

    @GetMapping("/token/refresh")
    public void getRefreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String authHeader = request.getHeader(AUTHORIZATION);
        if(authHeader != null && authHeader.startsWith("Bearer ")) {
            try {

                String refreshToken = authHeader.substring("Bearer ".length());
                Algorithm algorithm = Algorithm.HMAC256("secretkey".getBytes());
                JWTVerifier jwtVerifier = JWT.require(algorithm).build();
                DecodedJWT decodedJWT = jwtVerifier.verify(refreshToken);
                String email = decodedJWT.getSubject();
                AppUser appUser = userService.getAppUser(email);

                String accessToken = JWT.create().withSubject(appUser.getEmail()).withExpiresAt(new Date(System.currentTimeMillis()+ 20 * 60 * 1000))
                        .withIssuer(request.getRequestURL().toString())
                        .withClaim("roles", appUser.getRoles().stream().map(Role::getName).collect(Collectors.toList()))
                        .sign(algorithm);


                Map<String,String> tokens = new HashMap<>();
                tokens.put("accessToken",accessToken);
                tokens.put("refreshToken",refreshToken);

                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),tokens);

            } catch (Exception ex) {
                log.error("Error al loguearse : {}", ex.getMessage());
                response.setHeader("Error",ex.getMessage());
                response.setStatus(FORBIDDEN.value());

                Map<String,String> error = new HashMap<>();
                error.put("Error : ",ex.getMessage());

                response.setContentType(APPLICATION_JSON_VALUE);
                new ObjectMapper().writeValue(response.getOutputStream(),error);
            }
        }else {
            log.error("Refresh token missing");
            throw new RuntimeException("Refresh token missing");
        }
    }
}
