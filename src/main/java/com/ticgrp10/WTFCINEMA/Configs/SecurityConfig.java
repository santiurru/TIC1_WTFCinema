//package com.ticgrp10.WTFCINEMA.Configs;
//
//
//import com.ticgrp10.WTFCINEMA.Entities.Admin;
//import com.ticgrp10.WTFCINEMA.Repositories.AdminRepository;
//import com.ticgrp10.WTFCINEMA.Services.CustomUserDetailsService;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.SecurityFilterChain;
//
//import java.util.Date;
//
//@Configuration
//@EnableWebSecurity
//public class SecurityConfig {
//
//    @Bean
//    public CommandLineRunner commandLineRunnerAdmin(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
//        return args -> {
//            String email = "admin@prueba.com";
//            if (!adminRepository.existsByEmail(email)) {
//                Admin admin = Admin.builder()
//                        .name("Admin")
//                        .surname("User")
//                        .email(email)
//                        .birthDate(new Date())  // O la fecha que prefieras
//                        .password(passwordEncoder.encode("admin123"))  // Hardcodea la contraseña aquí
//                        .phoneNumber(123456789)
//                        .role("ROLE_ADMIN")  // Asegúrate de que tenga el rol correcto
//                        .build();
//
//                adminRepository.save(admin);
//                System.out.println("Admin creado con éxito");
//            } else {
//                System.out.println("El admin ya existe, no se ha creado un nuevo usuario.");
//            }
//        };
//    }
//
//    private final CustomUserDetailsService customUserDetailsService;
//    private final PasswordEncoder passwordEncoder;
//
//    public SecurityConfig(CustomUserDetailsService customUserDetailsService, PasswordEncoder passwordEncoder) {
//        this.customUserDetailsService = customUserDetailsService;
//        this.passwordEncoder = passwordEncoder;
//    }
//
//
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        return http
//                .authorizeHttpRequests(authRequest -> authRequest
//                        .requestMatchers("/").permitAll()
//                        .requestMatchers("/login").permitAll()
//                        .requestMatchers("/register").permitAll()
//                        .requestMatchers("/api/users/register").permitAll()
//                        .requestMatchers("/logout").permitAll()
//                        .requestMatchers("/Admin/**", "/Movies/**", "/Showings/**", "Snacks/**").hasRole("ADMIN")
//                        .requestMatchers("/listShowings/", "/listSnacks/", "/listMovies/").hasRole("USER")
//                        .anyRequest().authenticated()
//                )
//                .formLogin(form -> form
//                        .defaultSuccessUrl("/", true)
//                )
//                .logout(config -> config.logoutSuccessUrl("/"))
//                .build();
//    }
//
//    @Bean
//    public PasswordEncoder passwordEncoder() {
//        return new BCryptPasswordEncoder();
//    }
//
//    @Bean
//    public DaoAuthenticationProvider authenticationProvider() {
//        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
//        authProvider.setUserDetailsService(customUserDetailsService);
//        authProvider.setPasswordEncoder(passwordEncoder);
//        return authProvider;
//    }
//
//}
package com.ticgrp10.WTFCINEMA.Configs;

import com.ticgrp10.WTFCINEMA.Entities.Admin;
import com.ticgrp10.WTFCINEMA.Repositories.AdminRepository;
import com.ticgrp10.WTFCINEMA.Services.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import java.util.Date;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private CustomUserDetailsService customUserDetailsService;

    @Bean
    public CommandLineRunner commandLineRunnerAdmin(AdminRepository adminRepository, PasswordEncoder passwordEncoder) {
        return args -> {
            String email = "admin@prueba.com";
            if (!adminRepository.existsByEmail(email)) {
                Admin admin = Admin.builder()
                        .name("Admin")
                        .surname("User")
                        .email(email)
                        .birthDate(new Date())  // O la fecha que prefieras
                        .password(passwordEncoder.encode("admin123"))  // Hardcodea la contraseña aquí
                        .phoneNumber(123456789)
                        .role("ROLE_ADMIN")  // Asegúrate de que tenga el rol correcto
                        .build();

                adminRepository.save(admin);
                System.out.println("Admin creado con éxito");
            }
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
//                todo verificar nuevas entradas (como mostrar usuarios, etc)
                .authorizeHttpRequests(authRequest -> authRequest
                        .requestMatchers("/", "/login", "/register", "/api/users/register", "/logout","/error","/movie/current").permitAll()
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .requestMatchers(HttpMethod.POST,"/ratings/rate-movie","/booking/reserve/**","/booking/selectSeats/**").hasRole("USER")
                        .requestMatchers(HttpMethod.POST, "/movie/**","/showing/**","/snacks/**","/admin/**").hasRole("ADMIN") // Permite solo a ADMIN para crear
                        .requestMatchers("/admin/**", "/movie/**", "/showing/**", "/snacks/**","/login").hasRole("ADMIN")
                        .requestMatchers("/listShowings/", "snacks/list", "/listMovies/", "/booking/**", "ratings/**").hasRole("USER")
                        .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/login") // Indica la URL personalizada para el login
                        .defaultSuccessUrl("/") // Redirige tras un login exitoso
                        .permitAll())


                .logout(config -> config
                        .logoutSuccessUrl("/")
                        .permitAll());

//                .formLogin(form -> form
//                        .successHandler(new CustomAuthenticationSuccessHandler())
//                )
//                .logout(config -> config.logoutSuccessUrl("/"));

        return http.build();
    }
}