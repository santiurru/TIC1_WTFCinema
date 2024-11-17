package com.ticgrp10.WTFCINEMA.Controllers;

import com.ticgrp10.WTFCINEMA.Entities.Admin;
import com.ticgrp10.WTFCINEMA.Entities.RegisterDto;
import com.ticgrp10.WTFCINEMA.Entities.WebUser;
import com.ticgrp10.WTFCINEMA.Repositories.UserRepository;
import com.ticgrp10.WTFCINEMA.Services.AdminServices;
import com.ticgrp10.WTFCINEMA.Services.WebUserServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Date;
import java.util.Optional;

@Controller
@RequestMapping("/api/users")
public class WebUserController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AdminServices adminServices;
    @Autowired
    private WebUserServices webUserServices;

    @GetMapping("/register")
    public String register(Model model) {
        RegisterDto registerDto = new RegisterDto();
        model.addAttribute(registerDto);
        model.addAttribute("success", false);
        return "register";
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public String userList(Model model){
        model.addAttribute("users", userRepository.findAll());
        return "User/usersList";
    }

    @GetMapping("/menu")
    public String userMenu(){
        return "User/user";
    }

    //bookings
    @GetMapping("/bookings")
    @PreAuthorize("hasRole('USER')")
    public String indexBookings() {
        return "User/bookingsManagement";
    }

    @GetMapping("/bookings/list")
    @PreAuthorize("hasRole('USER')")
    public String listBookings() {
        return "redirect:/booking/myBookings";
    }

    @GetMapping("/bookings/create")
    @PreAuthorize("hasRole('USER')")
    public String createBookingForm() {
        return "redirect:/booking/reserve";
    }

    @GetMapping("/bookings/cancel")
    @PreAuthorize("hasRole('USER')")
    public String cancelBookingForm() {
        return "redirect:/booking/cancelForm";
    }

    //purchase snack
    @GetMapping("/snack/purchase")
    @PreAuthorize("hasRole('USER')")
    public String indexPurchaseSnack() {
        return "User/snackPurchaseManagement";
    }

    @GetMapping("/snack/list")
    @PreAuthorize("hasRole('USER')")
    public String listPurchaseSnack() {
        return "redirect:/purchase/snack/list";
    }

    @GetMapping("/snack/buy")
    @PreAuthorize("hasRole('USER')")
    public String createSnackForm() {
        return "redirect:/purchase/snack/create";
    }


    //profile
    @GetMapping("/profile")
    public String userProfile(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Obtener los detalles del usuario autenticado
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();

        Optional<WebUser> user = userRepository.findByEmail(userDetails.getUsername());
        model.addAttribute("user", user);

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

    @GetMapping("/creditCard")
    public String changeCreditCardForm(){
        return "User/createCreditCard";
    }

    @PostMapping("/creditCard")
    public String changeCreditCard(long cardNumber, String ownerName, String expirationDate, int cvv){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WebUser user = userRepository.findByEmail(authentication.getName()).get();
        webUserServices.addCreditCard(user, cardNumber, ownerName, expirationDate, cvv);
        return "redirect:/api/users/menu";
    }


    //rating
    @GetMapping("/ratings")
    @PreAuthorize("hasRole('USER')")
    public String indexRating() {
        return "User/ratingManagement";
    }

    @GetMapping("/ratings/rate")
    @PreAuthorize("hasRole('USER')")
    public String rateMovieForm(){
        return "redirect:/ratings/rate-movie";
    }

    @GetMapping("/ratings/list")
    @PreAuthorize("hasRole('USER')")
    public String listRatings() {
        return "redirect:/ratings/list";
    }

}