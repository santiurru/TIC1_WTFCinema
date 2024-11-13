package com.ticgrp10.WTFCINEMA.Controllers;


import com.ticgrp10.WTFCINEMA.Entities.*;
import com.ticgrp10.WTFCINEMA.Repositories.ShowingRepository;
import com.ticgrp10.WTFCINEMA.Repositories.SnackRepository;
import com.ticgrp10.WTFCINEMA.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping("/booking")
public class BookingController {

    @Autowired
    BookingService bookingServices;

    @Autowired
    MovieServices movieServices;

    @Autowired
    WebUserServices userServices;

    @Autowired
    ShowingServices showingServices;

    @Autowired
    SnackServices snackServices;

    @Autowired
    ShowingRepository showingRepository;

    @Autowired
    SnackRepository snackRepository;


    @GetMapping("/home/user")
    @PreAuthorize("hasRole('USER')")
    public String home() {
        return "User/bookingsManagement";
    }

    //reserve
    @GetMapping("/reserve")
    @PreAuthorize("hasRole('USER')")
    public String createBookingForm(Model model) {
        model.addAttribute("showings", showingServices.getAll());
        model.addAttribute("user", getCurrentUser());
        model.addAttribute("booking", new Booking());
        return "Bookings/createBooking";
    }

    @PostMapping("/reserve")
    @PreAuthorize("hasRole('USER')")
    public String createBooking(@RequestParam Long showingId,
                                @RequestParam List<Integer> seatIds,
                                @RequestParam WebUser currentUser ) {
        for (int seatId : seatIds) {
            Booking booking = new Booking();
            booking.setCustomerId(currentUser.getId());
            booking.setShowingId(showingRepository.findById(showingId).get().getId());
            booking.setSeatId(seatId);
            bookingServices.addBooking(booking);
        }

        return "redirect:/bookings/home/user";
    }

    //delete
    @GetMapping("/cancel/{showingId}")
    @PreAuthorize("hasRole('USER')")
    public String cancelForm(Model model) {
        WebUser userNow = getCurrentUser();
        model.addAttribute("user", userNow);
        model.addAttribute("bookings", bookingServices.getUserBookings(userNow));
        return "Bookings/deleteBooking";
    }

    @DeleteMapping("/cancel/{bookingId}")
    @PreAuthorize("hasRole('USER')")
    public String cancelBooking(Booking booking) {
        bookingServices.cancelBooking(getCurrentUser(), booking.getId());
        return "redirect:/bookings/home/user";
    }

    //list
    @GetMapping("/myBookings")
    @PreAuthorize("hasRole('USER')")
    public String getUserBookings(Model model) {
        WebUser currentUser = getCurrentUser();
        model.addAttribute("bookings", bookingServices.getUserBookings(currentUser));
        return "Bookings/listBookings";
    }

    // Obtener cines donde una película específica tiene funciones
    @GetMapping("/theatersByMovie")
    @ResponseBody
    public List<Theatre> getTheatersByMovie(@RequestParam Long movieId) {
        return showingServices.getTheatersByMovie(movieId);
    }
    @GetMapping("/movies")
    @ResponseBody
    public List<Movie> getMovies() {
        return showingServices.getMoviesByDate(LocalDateTime.now());
    }

    // Obtener días en los cuales hay funciones para una película en un cine específico
    @GetMapping("/daysByMovieAndTheater")
    @ResponseBody
    public List<LocalDateTime> getDaysByMovieAndTheater(@RequestParam Long movieId, @RequestParam Long theaterId) {
        return showingServices.getDaysByMovieAndTheater(movieId, theaterId);
    }

    // Obtener funciones disponibles en un día específico para una película y cine
    @GetMapping("/showingsByMovieTheaterAndDay")
    @ResponseBody
    public List<Showing> getShowingsByMovieTheaterAndDate(@RequestParam Long movieId, @RequestParam Long theaterId, @RequestParam LocalDateTime date) {
        return showingServices.getShowingsByMovieTheaterAndDate(movieId, theaterId, date);
    }

    @GetMapping("/availableSeatsByShowing")
    @ResponseBody
    public Map<String, String> getAvailableSeatsByShowing(@RequestParam Long showingId) {
        return showingServices.getSeatAvailability(showingId); // Deberías implementar este método en ShowingServices
    }


    private WebUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String email = authentication.getName();
        Optional<WebUser> userOptional = userServices.findByEmail(email);
        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException("User not found");
        }
        return userOptional.get();
    }
}
