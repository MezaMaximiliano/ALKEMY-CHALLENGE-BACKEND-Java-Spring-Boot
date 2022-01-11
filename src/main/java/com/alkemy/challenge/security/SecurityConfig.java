package com.alkemy.challenge.security;

import com.alkemy.challenge.token.CustomAuthenticationFilter;
import com.alkemy.challenge.token.CustomAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.http.HttpMethod.*;
import static org.springframework.security.config.http.SessionCreationPolicy.*;


@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserDetailsService userDetailsService;
    private final BCryptPasswordEncoder encoder;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        auth.userDetailsService(userDetailsService).passwordEncoder(encoder);
    }
//agregar permiso para eliminar usuario y rol ademas del metodo  /movies
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        CustomAuthenticationFilter customAuthenticationFilter = new CustomAuthenticationFilter(authenticationManagerBean());
        customAuthenticationFilter.setFilterProcessesUrl("/auth/login");

        http.csrf().disable();
        http.sessionManagement().sessionCreationPolicy(STATELESS);
        http.authorizeRequests().antMatchers("/auth/login", "/auth/token/refresh","/auth/register").permitAll();
        http.authorizeRequests().antMatchers(GET, "/auth/**").hasAnyAuthority("USER","ADMIN");
        http.authorizeRequests().antMatchers(POST, "/auth/**").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers(GET, "/characters/**").hasAnyAuthority("USER","ADMIN");
        http.authorizeRequests().antMatchers(POST, "/characters/**").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers(DELETE, "/characters/**").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers(GET, "/movies/**").hasAnyAuthority("USER","ADMIN");
        http.authorizeRequests().antMatchers(POST, "/movies/**").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers(DELETE, "/movies/**").hasAnyAuthority("ADMIN");
        http.authorizeRequests().antMatchers(GET, "/genders/**").hasAnyAuthority("USER","ADMIN");
        http.authorizeRequests().antMatchers(POST, "/genders/**").hasAnyAuthority("ADMIN");
        http.authorizeRequests().anyRequest().authenticated();
        http.addFilter(customAuthenticationFilter);
        http.addFilterBefore(new CustomAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class);
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean()throws Exception{
        return super.authenticationManagerBean();
    }
}
