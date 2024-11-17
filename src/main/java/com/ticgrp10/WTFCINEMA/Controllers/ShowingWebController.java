package com.ticgrp10.WTFCINEMA.Controllers;

import com.ticgrp10.WTFCINEMA.Entities.Movie;
import com.ticgrp10.WTFCINEMA.Entities.Room;
import com.ticgrp10.WTFCINEMA.Entities.Showing;
import com.ticgrp10.WTFCINEMA.Entities.Theatre;
import com.ticgrp10.WTFCINEMA.Services.MovieServices;
import com.ticgrp10.WTFCINEMA.Services.RoomService;
import com.ticgrp10.WTFCINEMA.Services.ShowingServices;
import com.ticgrp10.WTFCINEMA.Services.TheatreServices;
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
    private TheatreServices theatreServices;

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
        model.addAttribute("movies", movieServices.getAll());
        model.addAttribute("theatres", theatreServices.getAll());
        model.addAttribute("showing", new Showing());
        return "Showings/createShowing";
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public String createShowing(Showing showing, Model model) {
        // Verificar disponibilidad de la sala
        long length = movieServices.getMovieById(showing.getMovieId()).getLength();
        boolean isAvailable = showingServices.isRoomAvailable(showing.getRoomId(), showing.getDate(),length);
        if (!isAvailable) {
            model.addAttribute("error", "La sala no está disponible en esa hora.");
            model.addAttribute("movies", movieServices.getAll());
            model.addAttribute("theatres", theatreServices.getAll());
            model.addAttribute("showing", showing);
            return "Showings/createShowing"; // Volver al formulario si la sala no está disponible
        }

        // Si la sala está disponible, crear el showing
        showingServices.addShowing(showing);
        return "redirect:/showing/home/admin";
    }

    @GetMapping("/list")
    public String listShowings(Model model) {
        List<Showing> showings = showingServices.getAll();

        for (Showing showing : showings) {
            Movie movie = movieServices.getMovieById(showing.getMovieId());

            if (movie != null) {
                showing.setMovieTitle(movie.getTitle());
                showing.setMovieSynopsis(movie.getSynopsis());
            }

            Room room = roomServices.getRoomById(showing.getRoomId());
            if (room != null) {
                Theatre theatre = theatreServices.getTheatreById(room.getTheatreId());

                if (theatre != null) {
                    showing.setTheatreName(theatre.getNeighborhood());
                }
                showing.setRoomNumber(room.getNumber());
            }
        }

        model.addAttribute("showings", showings);
        return "Showings/listShowings";
    }

    @GetMapping("/roomsByCinema")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseBody
    public List<Room> getRoomsByCinema(@RequestParam("theatreId") Long theatreId) {
        return roomServices.getRoomsByTheatre(theatreId);
    }
}

