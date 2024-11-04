package com.ticgrp10.WTFCINEMA.Controllers;

import com.ticgrp10.WTFCINEMA.Entities.Movie;
import com.ticgrp10.WTFCINEMA.Entities.Room;
import com.ticgrp10.WTFCINEMA.Entities.Showing;
import com.ticgrp10.WTFCINEMA.Services.MovieServices;
import com.ticgrp10.WTFCINEMA.Services.RoomService;
import com.ticgrp10.WTFCINEMA.Services.ShowingServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

import java.util.List;

@Controller
@RequestMapping("/showing")
public class ShowingWebController {

    @Autowired
    private ShowingServices showingServices;

    @Autowired
    private MovieServices movieServices;

    @Autowired
    private RoomService roomServices;

    @GetMapping("/create")
    public String createShowingForm(Model model) {
        List<Movie> movies = movieServices.getAll(); // Obtener todas las películas
        List<Room> rooms = roomServices.getAll(); // Obtener todas las salas

        model.addAttribute("movies", movies);
        model.addAttribute("rooms", rooms); // Agregar salas al modelo
        model.addAttribute("showing", new Showing()); // Asegúrate de tener una entidad Showing
        return "Showings/createShowing"; // Ajusta la ruta según sea necesario
    }


    @PostMapping("/create")
    public String createShowing(Showing showing) {
        showingServices.addShowing(showing);
        return "Showings/moviesShowings";
    }

    @GetMapping("/list")
    public String listShowings(Model model) {
        model.addAttribute("showings", showingServices.getAll());
        return "Showings/listShowings";
    }
}

