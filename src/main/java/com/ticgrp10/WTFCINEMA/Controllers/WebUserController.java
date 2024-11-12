package com.ticgrp10.WTFCINEMA.Controllers;

import com.ticgrp10.WTFCINEMA.Entities.Admin;
import com.ticgrp10.WTFCINEMA.Entities.RegisterDto;
import com.ticgrp10.WTFCINEMA.Entities.WebUser;
import com.ticgrp10.WTFCINEMA.Repositories.UserRepository;
import com.ticgrp10.WTFCINEMA.Services.AdminServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private UserRepository repo;

    @Autowired
    private AdminServices adminServices;

    @GetMapping("/register")
    public String register(Model model) {
        RegisterDto registerDto = new RegisterDto();
        model.addAttribute(registerDto);
        model.addAttribute("success", false);
        return "register";
    }

    @PostMapping("/register")
    public String register(Model model, @Valid @ModelAttribute RegisterDto registerDto, BindingResult result) {
        if (!registerDto.getPassword().equals(registerDto.getConfirmPassword())) {
            result.addError(
                    new FieldError("registerDto", "confirmPassword", "Password and Confirm Password do not match")
            );
        }
        Optional<WebUser> user = repo.findByEmail(registerDto.getEmail());
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
            repo.save(newUser);

            model.addAttribute("registerDto", new RegisterDto());
            model.addAttribute("success", true);
        }
        catch (Exception e) {
            result.addError(new FieldError("registerDto",
                    "name", e.getMessage()));
        }
        return "register";
    }

    @GetMapping("/profile")
    public String profile(Model model) {return "profile";}

    @GetMapping("/home")
    @PreAuthorize("hasRole('USER')")
    public String userHome() {
        return "User/user";
    }

    //bookings
    @GetMapping("/bookings")
    @PreAuthorize("hasRole('USER')")
    public String indexBookings() {return "User/bookingsManagement";}

    @GetMapping("/bookings/myBookings")
    @PreAuthorize("hasRole('USER')")
    public String listBookings() {
        return "redirect:/booking/myBookings";
    }

    @GetMapping("/bookings/create")
    @PreAuthorize("hasRole('USER')")
    public String createBookingForm() {
        return "redirect:/booking/reserve";
    }


}