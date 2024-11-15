package com.ticgrp10.WTFCINEMA.Controllers;

import com.ticgrp10.WTFCINEMA.Entities.Movie;
import com.ticgrp10.WTFCINEMA.Entities.Rating;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/ratings")
public class RatingController {

    @Autowired
    private RatingServices ratingServices;

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RatingRepository ratingRepository;


    // Endpoint para mostrar la página con el formulario
    @GetMapping("/rate-movie")
    @PreAuthorize("hasRole('USER')")
    public String showRateMoviePage(Model model) {
        model.addAttribute("movies", movieRepository.findAll());
        return "User/movieRating";
    }

    // Endpoint para manejar el envío de la calificación
    @PostMapping("/rate-movie")
    @PreAuthorize("hasRole('USER')")
    public String rateMovie(@RequestParam Long movieId,
                            @RequestParam float rating,
                            Authentication authentication) {
        // Obtener el usuario logueado
        String email = authentication.getName();
        WebUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Crear un objeto Rating y asignar la película y el usuario logueado
        Movie movie = movieRepository.findById(movieId)
                .orElseThrow(() -> new RuntimeException("Película no encontrada"));

        Rating ratingToSave = new Rating();
        ratingToSave.setMovieId(movieId);
        ratingToSave.setCustomerId(user.getId());
        ratingToSave.setRating(rating);

        // Guardar la calificación
        ratingServices.addOrUpdateRating(ratingToSave);

        // Redirigir a una página de éxito o confirmación
        return "redirect:/api/users/ratings";  // Redirige a la página con un parámetro de éxito
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('USER')")
    public String showUserRatings(Model model, Authentication authentication) {
        // Obtener el usuario logueado
        String email = authentication.getName();
        WebUser user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Obtener todos los ratings del usuario logueado
        List<Rating> ratings = ratingRepository.findByCustomerId(user.getId());

        // Pasar los ratings al modelo para ser utilizados en la vista
        model.addAttribute("ratings", ratings);
        return "User/myRatings";  // Vista donde se mostrarán los ratings del usuario
    }
}


