package com.ticgrp10.WTFCINEMA.Controllers;

import com.ticgrp10.WTFCINEMA.Entities.*;
import com.ticgrp10.WTFCINEMA.Repositories.AdminRepository;
import com.ticgrp10.WTFCINEMA.Services.AdminServices;
import com.ticgrp10.WTFCINEMA.Services.MovieServices;
import com.ticgrp10.WTFCINEMA.Services.RatingServices;
import com.ticgrp10.WTFCINEMA.Services.WebUserServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/admin")
public class AdminWebController {

    @Autowired
    private AdminServices adminServices;
    @Autowired
    private WebUserServices repo;
    @Autowired
    private MovieServices movieServices;
    @Autowired
    private RatingServices ratingServices;



    @GetMapping("/home")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminHome() {
        return "Admin/admin";
    }

    @GetMapping("/user")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminUserManagement() {
        return "Admin/userManagement";
    }

    @GetMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public String createAdminForm(Model model) {
        RegisterDto registerDto = new RegisterDto();
        model.addAttribute(registerDto);
        model.addAttribute("success", false);
        return "Admin/createAdmin";
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public String createAdmin(Model model, @Valid @ModelAttribute RegisterDto registerDto, BindingResult result) {
        if (!registerDto.getPassword().equals(registerDto.getConfirmPassword())) {
            result.addError(
                    new FieldError("registerDto", "confirmPassword", "La contraseña se escribió diferente la segunda vez")
            );
        }

        Optional<Admin> admin = adminServices.findByEmail(registerDto.getEmail());
        Optional<WebUser> user = repo.findByEmail(registerDto.getEmail());
        if (admin.isPresent() || user.isPresent()) {
            result.addError(
                    new FieldError("registerDto", "email", "Este correo ya tiene una cuenta")
            );
        }

        if (result.hasErrors()) {
            return "Admin/createAdmin";
        }

        try {
            var bcryptEncoder = new BCryptPasswordEncoder();
            Admin newUser = new Admin();
            newUser.setName(registerDto.getName());
            newUser.setSurname(registerDto.getSurname());
            newUser.setEmail(registerDto.getEmail());
            newUser.setPhoneNumber(registerDto.getPhoneNumber());
            newUser.setBirthDate(registerDto.getBirthDate());
            newUser.setPassword(bcryptEncoder.encode(registerDto.getPassword()));
            adminServices.addAdmin(newUser);

            model.addAttribute("registerDto", new RegisterDto());
            model.addAttribute("success", true);
        }
        catch (Exception e) {
            result.addError(new FieldError("registerDto",
                    "name", e.getMessage()));
        }
        return "redirect:/admin/home";
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('ADMIN')")
    public String listAdmins(Model model) {
        model.addAttribute("admins", adminServices.getAll());
        return "Admin/listAdmins";}

    //movies
    @GetMapping("/movies")
    @PreAuthorize("hasRole('ADMIN')")
    public String indexMovies() {return "Admin/moviesManagement";}

    @GetMapping("/movies/list")
    @PreAuthorize("hasRole('ADMIN')")
    public String listMovies() {
        return "redirect:/movie/list";
    }

    @GetMapping("/movies/create")
    @PreAuthorize("hasRole('ADMIN')")
    public String createMovieForm() {
        return "redirect:/movie/create";
    }

    //showings
    @GetMapping("/showings")
    @PreAuthorize("hasRole('ADMIN')")
    public String indexShowings() {return "Admin/showingsManagement";}

    @GetMapping("/showings/create")
    @PreAuthorize("hasRole('ADMIN')")
    public String createShowingForm() {return "redirect:/showing/create";}

    @GetMapping("/showings/list")
    @PreAuthorize("hasRole('ADMIN')")
    public String listShowingsByMovie() {return "redirect:/showing/list";
    }

    //snacks
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/snacks")
    public String indexSnacks() {
        return "Admin/snacksManagement";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/snacks/list")
    public String listSnacks() {
        return "redirect:/snacks/list";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/snacks/create")
    public String createSnackForm() {
        return "redirect:/snacks/create";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/profile")
    public String profile(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        Optional<Admin> admin = adminServices.findByEmail(userDetails.getUsername());
        if (admin.isPresent()){
            model.addAttribute("user", admin);
        }
        return "Admin/adminProfile";
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/allRatings")
    public String allRatings(Model model){
        List<Movie> allMovies = movieServices.getAll();
        allMovies.sort((movie1, movie2) -> {
            float avgRating1 = ratingServices.calculateAverageRating(movie1.getId());
            float avgRating2 = ratingServices.calculateAverageRating(movie2.getId());
            return Float.compare(avgRating2, avgRating1); // Descendente
        });

        // Agrega las películas ordenadas al modelo
        model.addAttribute("movies", allMovies);

        return "Admin/allRatings";
    }
}

