package com.ticgrp10.WTFCINEMA.Controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ticgrp10.WTFCINEMA.Entities.*;
import com.ticgrp10.WTFCINEMA.Repositories.ShowingRepository;
import com.ticgrp10.WTFCINEMA.Repositories.SnackRepository;
import com.ticgrp10.WTFCINEMA.Services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

    @Autowired
    SeatServices seatServices;


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
    public String createBooking(@RequestParam long showingId) {
        return "redirect:/booking/selectSeats/" + showingId;
    }

    @GetMapping("/selectSeats/{showingId}")
    @PreAuthorize("hasRole('USER')")
    public String selectSeatsForm(@PathVariable("showingId") Long showingId, Model model) {
        List<String> occupiedSeats = seatServices.getSeatsByShowing(showingId).stream()
                .map(seat -> seat.getSeatRow() + "," + seat.getSeatColumn()) // "row,column"
                .collect(Collectors.toList());//

        model.addAttribute("showingId", showingId);
        model.addAttribute("occupiedSeats", occupiedSeats);

        return "Bookings/seatSelection";
    }

    @PostMapping("/bookSeats")
    @PreAuthorize("hasRole('USER')")
    public String bookSeats(@RequestParam("showingId") Long showingId,
                            @RequestParam("selectedSeats") String selectedSeatsJson) {

        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Integer>> selectedSeats;
        try {
            selectedSeats = objectMapper.readValue(selectedSeatsJson, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing selected seats JSON", e);
        }
        WebUser currentUser = getCurrentUser();
        Optional<Booking> bookingAuxOpt = bookingServices.getBookingByCustomerAndShowing(currentUser.getId(), showingId);
        if(bookingAuxOpt.isEmpty()) {
            Booking booking = new Booking();
            booking.setCustomerId(currentUser.getId());
            booking.setShowingId(showingId);
            bookingServices.addBooking(booking);
        }
        Booking bookingAux = bookingServices.getBookingByCustomerAndShowing(currentUser.getId(), showingId).get();

        selectedSeats.forEach(seat -> {
            int row = seat.get("row");
            int col = seat.get("col");
            seatServices.bookSeat(bookingAux.getId(), row, col); // Implementa este método en tu servicio
        });

        return "User/buySnackQuestion";
    }


    //cancelar
    @GetMapping("/cancelForm")
    @PreAuthorize("hasRole('USER')")
    public String getCancelForm(){
        return "Bookings/cancelBookingForm";
    }

    @PostMapping("/cancelSeats")
    @PreAuthorize("hasRole('USER')")
    public String cancelSeats(@RequestParam Long bookingId,
            @RequestParam List<Long> seatIds,
            RedirectAttributes redirectAttributes) {

        WebUser user = getCurrentUser();
        boolean success = seatServices.cancelSeats(bookingId, seatIds, user.getId());

        if (success) {
            redirectAttributes.addFlashAttribute("message", "Asientos cancelados exitosamente.");
        } else {
            redirectAttributes.addFlashAttribute("error", "No se pudieron cancelar los asientos.");
        }
        return "redirect:/booking/home/user";
    }

//    //list
//    @GetMapping("/myBookings")
//    @PreAuthorize("hasRole('USER')")
//    public String getUserBookings(Model model) {
//        WebUser currentUser = getCurrentUser();
//        model.addAttribute("bookings", bookingServices.getUserBookings(currentUser));
//        return "Bookings/listBookings";
//    }


    @GetMapping("/getSeats")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Seat>> getSeats(@RequestParam("showingId") Long showingId) {
        List<Seat> seats = seatServices.getSeatsByShowing(showingId);
        return ResponseEntity.ok(seats);
    }

    @GetMapping("/getBookings")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Booking>> getBookingsForUser() {
        WebUser user = getCurrentUser();
        List<Booking> bookings = bookingServices.getBookingsByCustomerId(user.getId());
        return ResponseEntity.ok(bookings);
    }

    @GetMapping("/getSeats/{bookingId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Seat>> getSeatsForBooking(@PathVariable Long bookingId) {
        List<Seat> seats = seatServices.getSeatsByBookingId(bookingId);
        return ResponseEntity.ok(seats);
    }

    @GetMapping("/theatersByMovieTest")
    @ResponseBody
    public List<Theatre> getTheatres(@RequestParam Long movieId) {
        return showingRepository.findTheatres(movieId,LocalDateTime.now());
    }
    @GetMapping("/movies")
    @ResponseBody
    public List<Movie> getMovies() {
        return showingServices.getMoviesByDate(LocalDateTime.now());
    }


    @GetMapping("/daysByMovieAndTheaterTest")
    @ResponseBody
    public List<LocalDateTime> getDaysByMovieAndTheaterTest(@RequestParam Long movieId, @RequestParam Long theatreId) {
        return showingRepository.findDateByMovieAndTheatre(movieId,theatreId,LocalDateTime.now());
    }

    @GetMapping("/showingsByMovieTheaterAndDay")
    @ResponseBody
    public List<Showing> getShowingsByMovieTheaterAndDate(@RequestParam Long movieId, @RequestParam Long theatreId, @RequestParam LocalDateTime date) {
        return showingRepository.findShowingByMovieAndTheatreAndDate(movieId,theatreId,date);
    }

    @GetMapping("/availableSeatsByShowing")
    @ResponseBody
    public Map<String, String> getAvailableSeatsByShowing(@RequestParam Long showingId) {
        return showingServices.getSeatAvailability(showingId); // implementar en ShowingServices (?
    }

    //obtener el usuario en session
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
