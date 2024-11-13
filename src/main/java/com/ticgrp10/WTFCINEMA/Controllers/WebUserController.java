package com.ticgrp10.WTFCINEMA.Controllers;

import com.ticgrp10.WTFCINEMA.Entities.Admin;
import com.ticgrp10.WTFCINEMA.Entities.RegisterDto;
import com.ticgrp10.WTFCINEMA.Entities.WebUser;
import com.ticgrp10.WTFCINEMA.Repositories.UserRepository;
import com.ticgrp10.WTFCINEMA.Services.AdminServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/api/users")
public class WebUserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdminServices adminServices;

    @GetMapping("/register")
    public String register(Model model) {
        RegisterDto registerDto = new RegisterDto();
        model.addAttribute(registerDto);
        model.addAttribute("success", false);
        return "register";
    }

    @GetMapping("/list")
    public String userList(Model model){
        model.addAttribute("users", userRepository.findAll());
        return "User/usersList";
    }


    @GetMapping("/menu")
    public String usermenu(){
        return "User/user";
    }

    @GetMapping("/bookings")
    public String userBookings(){
        return "User/userBookings";
    }

    @GetMapping("/profile")
    public String userProfile(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Obtener los detalles del usuario autenticado
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        // Supongamos que tienes un servicio para obtener los datos adicionales del usuario
        Optional<WebUser> user = userRepository.findByEmail(userDetails.getUsername());
        if (user.isPresent()){
            model.addAttribute("user", user);
        }else{
            Optional<Admin> admin = adminServices.findByEmail(userDetails.getUsername());
            if (admin.isPresent()){
                model.addAttribute("user", admin);
            }
        }
        return "User/profile";
    }

    @PostMapping("/register")
    public String register(Model model, @Valid @ModelAttribute RegisterDto registerDto, BindingResult result) {
        if (!registerDto.getPassword().equals(registerDto.getConfirmPassword())) {
            result.addError(
                    new FieldError("registerDto", "confirmPassword", "Password and Confirm Password do not match")
            );
        }

        Optional<WebUser> user = userRepository.findByEmail(registerDto.getEmail());
        Optional<Admin> admin = adminServices.findByEmail(registerDto.getEmail());
        if (user.isPresent() || admin.isPresent()) {
            result.addError(
                    new FieldError("registerDto", "email", "Email address is already used")
            );
        }

        if (result.hasErrors()) {
            return "register";
        }

        try {
            var bcryptEncoder = new BCryptPasswordEncoder();

            WebUser newUser = new WebUser();
            newUser.setName(registerDto.getName());
            newUser.setSurname(registerDto.getSurname());
            newUser.setEmail(registerDto.getEmail());
            newUser.setPhoneNumber(registerDto.getPhoneNumber());
            newUser.setBirthDate(registerDto.getBirthDate());
            newUser.setPassword(bcryptEncoder.encode(registerDto.getPassword()));
            userRepository.save(newUser);

            model.addAttribute("registerDto", new RegisterDto());
            model.addAttribute("success", true);
        }
        catch (Exception e) {
            result.addError(new FieldError("registerDto",
                    "name", e.getMessage()));
        }




        return "register";
    }


//    @Autowired
//    private PasswordEncoder passwordEncoder;

//    @GetMapping("/register")
//    public String register() {
//        return "register";
//    }
//
//    @PostMapping("/register")
//    public ResponseEntity<String> registerUser(@RequestBody @Valid WebUser appUser) {
//        if (appUserService.findByEmail(appUser.getEmail()).isPresent()) {
//            return ResponseEntity.status(HttpStatus.CONFLICT)
//                    .body("El usuario ya existe con este correo electrónico.");
//        }
//        appUserService.addUser(appUser);
//        return ResponseEntity.status(HttpStatus.CREATED)
//                .body("Usuario registrado con éxito: " + appUser.getEmail());
//    }

//    @GetMapping("/login")
//    public ResponseEntity<String> login(@RequestParam String email, @RequestParam String password) {
//        Optional<WebUser> user = appUserService.findByEmail(email);
////        if (user.isPresent() && passwordEncoder.matches(password, user.get().getPassword())) {
////            return ResponseEntity.ok("Inicio de sesión exitoso para: " + user.get().getEmail());
////        }
//        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
//                .body("Credenciales incorrectas.");
//    }
}