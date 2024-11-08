package com.ticgrp10.WTFCINEMA.Controllers;

import com.ticgrp10.WTFCINEMA.Entities.Movie;
import com.ticgrp10.WTFCINEMA.Entities.Room;
import com.ticgrp10.WTFCINEMA.Entities.Showing;
import com.ticgrp10.WTFCINEMA.Services.MovieServices;
import com.ticgrp10.WTFCINEMA.Services.RoomService;
import com.ticgrp10.WTFCINEMA.Services.ShowingServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
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

    @GetMapping("/home/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String home() {
        return "Admin/showingsManagement";
    }

    @GetMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public String createShowingForm(Model model) {
        List<Movie> movies = movieServices.getAll();
        List<Room> rooms = roomServices.getAll();
        model.addAttribute("movies", movies);
        model.addAttribute("rooms", rooms);
        model.addAttribute("showing", new Showing());
        return "Showings/createShowing";
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public String createShowing(Showing showing) {
        showingServices.addShowing(showing);
        return "redirect:/showing/home/admin";
    }

    @GetMapping("/list")
    public String listShowings(Model model) {
        model.addAttribute("showings", showingServices.getAll());
        return "Showings/listShowings";
    }

    @GetMapping("/roomsByCinema")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public List<Room> getRoomsByCinema(@RequestParam("cinemaId") Long cinemaId) {
        return roomServices.getRoomsByTheatre(cinemaId);
    }
}

