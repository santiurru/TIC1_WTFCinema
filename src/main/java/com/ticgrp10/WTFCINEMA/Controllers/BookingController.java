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

import java.time.LocalDateTime;
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

    @Autowired
    SeatServices seatServices;


    //    @PostMapping("/selectSeats")
//    @PreAuthorize("hasRole('USER')")
//    public String selectSeats(@PathVariable("showingId") Long showingId, @RequestParam List<Integer> seatIds){
//        WebUser currentUser = getCurrentUser();
//
////        for (int seatId : seatIds) {
////            Booking booking = new Booking();
////            booking.setCustomerId(currentUser.getId());
////            booking.setShowingId(showingRepository.findById(showingId).get().getId());
////            booking.setSeatId(seatId);
////            bookingServices.addBooking(booking);
////        }
//        return "User/buySnackQuestion";
//    }
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
//        model.addAttribute("showingId", showingId);
        return "redirect:/booking/selectSeats/" + showingId;
    }

    @GetMapping("/selectSeats/{showingId}")
    @PreAuthorize("hasRole('USER')")
    public String selectSeatsForm(@PathVariable("showingId") Long showingId, Model model) {
        List<Seat> occupiedSeats = seatServices.getSeatsByShowing(showingId); // Lista de asientos ocupados
        model.addAttribute("showingId", showingId);
        model.addAttribute("occupiedSeats", occupiedSeats); // Enviar los asientos ocupados
        return "Bookings/seatSelection";
    }




    @PostMapping("/bookSeats")
    @PreAuthorize("hasRole('USER')")
    public String bookSeats(@RequestParam("showingId") Long showingId,
                            @RequestParam("selectedSeats") String selectedSeatsJson) {
        // Convertir el JSON recibido a una lista de asientos seleccionados
        ObjectMapper objectMapper = new ObjectMapper();
        List<Map<String, Integer>> selectedSeats;
        try {
            selectedSeats = objectMapper.readValue(selectedSeatsJson, new TypeReference<>() {});
        } catch (JsonProcessingException e) {
            throw new RuntimeException("Error parsing selected seats JSON", e);
        }
        WebUser currentUser = getCurrentUser();
        Booking booking = new Booking();
        booking.setCustomerId(currentUser.getId());
        booking.setShowingId(showingId);
        bookingServices.addBooking(booking);
        Booking bookingAux = bookingServices.getBookingByCustomerAndShowing(currentUser.getId(), showingId);
        // Procesar los asientos seleccionados (ej. guardarlos en la base de datos)
        selectedSeats.forEach(seat -> {
            int row = seat.get("row");
            int col = seat.get("col");
            seatServices.bookSeat(bookingAux.getId(), row, col); // Implementa este método en tu servicio
        });

        return "redirect:/showings";
    }



//    @PostMapping("/reserve")
//    @PreAuthorize("hasRole('USER')")
//    public String createBooking(@RequestParam Long showingId, HttpSession session) {
//        // Guardar el showingId en la sesión
//        session.setAttribute("showingId", showingId);
//        return "redirect:/booking/selectSeats"; // Redirigir a la página de selección de asientos
//    }
//
//    // Método para mostrar el formulario de selección de asientos
//    @GetMapping("/selectSeats")
//    @PreAuthorize("hasRole('USER')")
//    public String selectSeatsForm(HttpSession session, Model model) {
//        // Recuperar el showingId de la sesión
//        Long showingId = (Long) session.getAttribute("showingId");
//
//        // Verificar si showingId es null (es decir, si no está en la sesión)
//        if (showingId == null) {
//            return "redirect:/reserve"; // Redirigir a la página de reserva si no hay un showingId válido
//        }
//
//        System.out.println("Accediendo a selectSeatsForm con Showing ID: " + showingId);
//
//        // Obtener los asientos disponibles desde el servicio
//        Map<String, String> seatMap = showingServices.getSeatAvailability(showingId);
//
//        // Agregar atributos al modelo para la vista
//        model.addAttribute("showingId", showingId);
//        model.addAttribute("seatMap", seatMap);
//
//        return "Bookings/seatSelection"; // Nombre de la plantilla en `templates/Bookings/seatSelection.html`
//    }

    @GetMapping("/getSeats")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<Seat>> getSeats(@RequestParam("showingId") Long showingId) {
        List<Seat> seats = seatServices.getSeatsByShowing(showingId); // Llama al servicio
        return ResponseEntity.ok(seats); // Retorna los asientos
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

    // Obtener días en los cuales hay funciones para una película en un cine específico
    @GetMapping("/daysByMovieAndTheater")
    @ResponseBody
    public List<LocalDateTime> getDaysByMovieAndTheater(@RequestParam Long movieId, @RequestParam Long theaterId) {
        return showingServices.getDaysByMovieAndTheater(movieId, theaterId);
    }

    @GetMapping("/daysByMovieAndTheaterTest")
    @ResponseBody
    public List<LocalDateTime> getDaysByMovieAndTheaterTest(@RequestParam Long movieId, @RequestParam Long theatreId) {
        return showingRepository.findDateByMovieAndTheatre(movieId,theatreId,LocalDateTime.now());
    }

    // Obtener funciones disponibles en un día específico para una película y cine
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
