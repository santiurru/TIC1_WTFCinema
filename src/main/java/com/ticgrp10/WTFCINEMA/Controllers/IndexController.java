package com.ticgrp10.WTFCINEMA.Controllers;

import com.ticgrp10.WTFCINEMA.Entities.Movie;
import com.ticgrp10.WTFCINEMA.Entities.Theatre;
import com.ticgrp10.WTFCINEMA.Services.MovieServices;
import com.ticgrp10.WTFCINEMA.Services.TheatreServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class IndexController
{
    @Autowired
    private MovieServices movieServices;

    @Autowired
    private TheatreServices theatreServices;

    @GetMapping({"", "/"})
    public String index(Model model) {
        List<Movie> movies = movieServices.getAll();
        model.addAttribute("movies", movies);
        List<Theatre> theatres = theatreServices.getAll();
        model.addAttribute("theatres", theatres);
        return "index1";
    }

    @GetMapping("/error")
    public String error() {return "error1";}

}

