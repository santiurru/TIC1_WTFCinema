package com.ticgrp10.WTFCINEMA.Controllers;

import com.ticgrp10.WTFCINEMA.Entities.Movie;
import com.ticgrp10.WTFCINEMA.Entities.Rating;
import com.ticgrp10.WTFCINEMA.Entities.Theatre;
import com.ticgrp10.WTFCINEMA.Entities.WebUser;
import com.ticgrp10.WTFCINEMA.Repositories.RatingRepository;
import com.ticgrp10.WTFCINEMA.Services.RatingServices;
import com.ticgrp10.WTFCINEMA.Repositories.MovieRepository;
import com.ticgrp10.WTFCINEMA.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RatingRepository ratingRepository;

    @Autowired
    private RatingServices ratingServices;

    //hacer rating
    @GetMapping("/rate-movie")
    @PreAuthorize("hasRole('USER')")
    public String showRateMoviePage(Model model) {
        model.addAttribute("movies", movieRepository.findAll());
        return "User/movieRating";
    }

    @PostMapping("/rate-movie")
    @PreAuthorize("hasRole('USER')")
    public String rateMovie(@RequestParam Long movieId,
                            @RequestParam float rating,
                            Authentication authentication) {

        String email = authentication.getName();
        WebUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Película no encontrada"));

        Rating ratingToSave = new Rating();
        ratingToSave.setMovieId(movieId);
        ratingToSave.setCustomerId(user.getId());
        ratingToSave.setRating(rating);

        ratingServices.addOrUpdateRating(ratingToSave);

        return "redirect:/api/users/ratings";
    }

    //ver ratings
    @GetMapping("/list")
    @PreAuthorize("hasRole('USER')")
    public String showUserRatings(Model model, Authentication authentication) {
        String email = authentication.getName();
        WebUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        List<Rating> ratings = ratingRepository.findByCustomerId(user.getId());
        ratings.forEach(rating -> rating.setMovieTitle(movieRepository.findById(rating.getMovieId()).get().getTitle()));


        model.addAttribute("ratings", ratings);
        return "User/myRatings";
    }

    //average
    @GetMapping("/average")
    @ResponseBody
    public Float getAverage(@RequestParam long movieId) {
        return ratingServices.calculateAverageRating(movieId);
    }
}


