package com.ticgrp10.WTFCINEMA.Controllers;

import com.ticgrp10.WTFCINEMA.Entities.WebUser;
import com.ticgrp10.WTFCINEMA.Services.WebUserServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class WebUserController {

    @Autowired
    private WebUserServices appUserService;

//    @Autowired
//    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody @Valid WebUser appUser) {
        if (appUserService.findByEmail(appUser.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("El usuario ya existe con este correo electrónico.");
        }
        appUserService.addUser(appUser);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body("Usuario registrado con éxito: " + appUser.getEmail());
    }

    @GetMapping("/login")
    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
        Optional<WebUser> user = appUserService.findByEmail(email);
//        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
//            return ResponseEntity.ok("Inicio de sesión exitoso para: " + user.get().getEmail());
//        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body("Credenciales incorrectas.");
    }
}