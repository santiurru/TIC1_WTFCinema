package com.ticgrp10.WTFCINEMA.Controllers;


import com.ticgrp10.WTFCINEMA.Entities.Booking;
import com.ticgrp10.WTFCINEMA.Entities.WebUser;
import com.ticgrp10.WTFCINEMA.Services.BookingService;
import com.ticgrp10.WTFCINEMA.Services.ShowingServices;
import com.ticgrp10.WTFCINEMA.Services.WebUserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@Controller
@RequestMapping("/bookings")
public class BookingController {

    @Autowired
    BookingService bookingServices;

    @Autowired
    WebUserServices userServices;

    @Autowired
    ShowingServices showingServices;

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
    public String createBooking(Booking booking) {
        bookingServices.addBooking(booking);
        return "redirect:/bookings/home/user";
    }

    //delete
    @GetMapping("/cancel/{showingId}")
    @PreAuthorize("hasRole('USER')")
    public String cancelForm(Model model) {
        WebUser userNow = getCurrentUser();
        model.addAttribute("user", userNow);
        model.addAttribute("bookings", bookingServices.getUserBookings(userNow));
        return "Bookings/cancelBooking";
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
