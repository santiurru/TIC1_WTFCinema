package com.ticgrp10.WTFCINEMA.Controllers;

import com.ticgrp10.WTFCINEMA.Entities.Movie;
import com.ticgrp10.WTFCINEMA.Entities.Showing;
import com.ticgrp10.WTFCINEMA.Entities.Theatre;
import com.ticgrp10.WTFCINEMA.Services.MovieServices;
import com.ticgrp10.WTFCINEMA.Services.ShowingServices;
import com.ticgrp10.WTFCINEMA.Services.TheatreServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

@Controller
public class IndexController
{
    @Autowired
    private MovieServices movieServices;

    @Autowired
    private ShowingServices showingServices;

    @Autowired
    private TheatreServices theatreServices;

    @GetMapping({"", "/"})
    public String index(Model model) {
        List<Showing> funciones = showingServices.getAll();
        List<Movie> temp = new LinkedList<>();
        for (Showing funcion: funciones){
            Movie pelicula = movieServices.getMovieById(funcion.getMovieId());
            if ((funcion.getDate().isAfter(LocalDateTime.now())) && !temp.contains(pelicula)){
                temp.add(pelicula);
            }
        }
        model.addAttribute("movies", temp);
        List<Theatre> theatres = theatreServices.getAll();
        model.addAttribute("theatres", theatres);
        return "index1";
    }

    @GetMapping("/error")
    public String error() {
        return "error1";
    }

    @GetMapping("/login")
    public String login(){
        return "login";
    }

}

