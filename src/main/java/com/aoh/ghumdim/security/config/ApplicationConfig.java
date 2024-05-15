package com.aoh.ghumdim.security.config;

import com.aoh.ghumdim.security.entity.User;
import com.aoh.ghumdim.security.repo.UserRepository;
import com.aoh.ghumdim.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private final UserRepository repository;
    private final JwtService jwtService;

    //get user id to map
//    @Bean
//    public Integer findUserId(){
////        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
////        log.info();
//        String token = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJyaW1AZC5kIiwidGVzdCI6InJpbWVzaCBkYWkiLCJpYXQiOjE3MDk4MzEwMjQsImV4cCI6MTcwOTgzNDAyNH0.TLeeOidYi0EJcZtiCNUq_LY6RKlAmU33dDeAbgPxQHY";
//        Optional<User> user = repository.findByEmail(jwtService.extractUsername(token));
//        log.info("app conf");
//        log.info(jwtService.extractUsername(token));
//        return user.get().getId();
//    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> repository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }


    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
