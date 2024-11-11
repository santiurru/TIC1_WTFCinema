package com.ticgrp10.WTFCINEMA.Controllers;

import com.ticgrp10.WTFCINEMA.Entities.Movie;
import com.ticgrp10.WTFCINEMA.Entities.Showing;
import com.ticgrp10.WTFCINEMA.Services.MovieServices;
import com.ticgrp10.WTFCINEMA.Services.ShowingServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Controller
@RequestMapping("/movie")
public class MovieWebController {

    @Autowired
    private MovieServices movieServices;

    @Autowired
    private ShowingServices showingServices;

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
        model.addAttribute("movies",movieServices.getAll());
        return "Movies/listMovies";
    }

    @GetMapping("/current")
    public String currentMovies(Model model){
        List<Showing> funciones = showingServices.getAll();
        List<Movie> temp = new LinkedList<>();
        for (Showing funcion: funciones){
            Movie pelicula = movieServices.getMovieById(funcion.getMovieId());
            //todo cambiar lo de la hora
            if ((funcion.getDate().isAfter(LocalDateTime.now())) && !temp.contains(pelicula)){
                temp.add(pelicula);
            }
        }
        model.addAttribute("movies", temp);
        if (temp.isEmpty()){
            model.addAttribute("vacio","No hay peliculas para mostrar");
        }
        return "Movies/listMovies";
    }
}

