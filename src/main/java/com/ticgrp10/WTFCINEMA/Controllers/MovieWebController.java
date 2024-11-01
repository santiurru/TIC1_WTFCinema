package com.ticgrp10.WTFCINEMA.Controllers;

import com.ticgrp10.WTFCINEMA.Entities.Movie;
import com.ticgrp10.WTFCINEMA.Services.MovieServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/movie")
public class MovieWebController {

    @Autowired
    private MovieServices movieServices;
    @GetMapping("/home/admin")
    public String home() {
        return "movies";
    }

    @GetMapping("/create")
    public String createMovieForm(Model model) {
        model.addAttribute("movie", new Movie());
        return "Admin/createMovie";
    }

    @PostMapping("/create")
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

