package com.ticgrp10.WTFCINEMA.Controllers;

import com.ticgrp10.WTFCINEMA.Entities.Movie;
import com.ticgrp10.WTFCINEMA.Services.MovieServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Controller
@RequestMapping("/movie")
public class MovieWebController {

    @Autowired
    private MovieServices movieServices;

    @GetMapping("/home/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String home() {
        return "Admin/moviesManagement";
    }

    @GetMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public String createMovieForm(Model model) {
        model.addAttribute("movie", new Movie());
        return "Movies/createMovie";
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public String createMovie(Movie movie) {
        movieServices.addMovie(movie);
        return "redirect:/movie/home/admin";
    }
    @GetMapping("/list")
    public String listMovies(Model model) {
        model.addAttribute("movies", movieServices.getAll());
        return "Movies/listMovies";
    }
}

