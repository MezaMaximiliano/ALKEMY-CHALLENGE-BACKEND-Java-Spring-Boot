package com.alkemy.challenge.service;

import com.alkemy.challenge.entity.AppUser;
import com.alkemy.challenge.entity.Role;
import com.alkemy.challenge.mail.SendEmailService;
import com.alkemy.challenge.repository.AppUserRepository;
import com.alkemy.challenge.repository.RoleRepository;
import com.alkemy.challenge.util.Util;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class AppUserServiceImpl  implements AppUserService, UserDetailsService {

    private final AppUserRepository userRepository;
    private final RoleRepository roleRepository;
    private final BCryptPasswordEncoder encoder;
    private final SendEmailService sendEmailService;

    private final String _SUBJET = "Registro exitoso";
    private final String _MESSAGE = "%s, te damos la bienvenida al Mundo de Disney!!\n" +
            "Usuario: %s\nContraseña: %s";


    @Override
    public AppUser saveUser(AppUser appUser) {

        if(userRepository.findByEmail(appUser.getEmail()) != null){
            log.error("El email ya se encuentra registrado.");
            return null;
        }

        String message = Util.format(_MESSAGE,appUser.getName(),appUser.getEmail(),appUser.getPassword());

        appUser.setPassword(encoder.encode(appUser.getPassword()));
        userRepository.save(appUser);
        log.info("Nuevo usuario: {} guardado en la base de datos", appUser.getName());

        setRoleToUser(appUser);//Se agrega roles al usuario que se registra

        try {
            sendEmailService.sendEmailMessage(_SUBJET, message,appUser.getEmail());
        } catch (Exception err) {
            log.error(err.getMessage());
        }

        return appUser;
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Nuevo rol: {} guardado en la base de datos",role.getName());
        return roleRepository.save(role);
    }

    @Override
    public void addRoleToUser(String email, String rolName) {
        AppUser user = userRepository.findByEmail(email);
        user.getRoles().add(roleRepository.findByName(rolName));
        log.info("Rol {} agregado al usuario {}",rolName,email);

    }

    @Override
    public AppUser getAppUser(String email) {
        log.info("Trayendo al usuario {}",email);
        return userRepository.findByEmail(email);
    }

    @Override
    public List<AppUser> getAppUsers() {
        log.info("Trayendo lista de usuarios");
        return userRepository.findAll();
    }

    @Override
    public List<Role> findAllRoles() {
        log.info("Trayendo lista de roles");
        return roleRepository.findAll();
    }

    @SneakyThrows
    @Override
    public void disableUser(Long id) {
        if(userRepository.findById(id).isPresent()){
            userRepository.disableUser(id);
        }else {
            throw new Exception("Usuario no encontrado");
        }
    }



    @SneakyThrows
    @Override
    public void enableUser(Long id){
        if(userRepository.findById(id).isPresent()){
            userRepository.enableUser(id);
        }else {
            throw new Exception("Usuario no encontrado");
        }
    }

    @SneakyThrows
    @Override
    public Optional<AppUser> findById(Long id){
        return userRepository.findById(id);
  }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        AppUser appUser = userRepository.findByEmail(email);
        if(appUser==null){
            log.error("Usuario no encotrado");
            throw new UsernameNotFoundException("Usuario no encotrado");
        }else{
            log.info("Usuario encotrado");
        }
        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();

        for(Role role: appUser.getRoles()){
            authorities.add(new SimpleGrantedAuthority(role.getName()));
        }

        return new User(appUser.getEmail(), appUser.getPassword(), authorities);
    }

    private void setRoleToUser(AppUser appUser){
        if(getAppUsers().size() == 1){
            addRoleToUser(appUser.getEmail(),"ADMIN");
            addRoleToUser(appUser.getEmail(),"USER");
        }

        if(appUser.getRoles().isEmpty()){
            addRoleToUser(appUser.getEmail(),"USER");
        }
    }

}

