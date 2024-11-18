package com.ticgrp10.WTFCINEMA.Controllers;

import com.ticgrp10.WTFCINEMA.Entities.*;
import com.ticgrp10.WTFCINEMA.Repositories.*;
import com.ticgrp10.WTFCINEMA.Services.BookingService;
import com.ticgrp10.WTFCINEMA.Services.PurchaseSnackService;
import com.ticgrp10.WTFCINEMA.Services.SeatServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/api/checkout")
public class CheckoutController {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private PurchaseSnackRepository purchaseSnackRepository;
    @Autowired
    private ShowingRepository showingRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    SnackRepository snackRepository;
    @Autowired
    SeatServices seatServices;
    @Autowired
    PurchaseSnackService purchaseSnackServices;

    @GetMapping
    public String checkoutPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WebUser user = userRepository.findByEmail(authentication.getName()).orElse(null);

        if (user == null) {
            return "redirect:/login";
        }

        List<Seat> seats = seatRepository.getSeatsByUserIdAndPaid(user.getId(), false);
        List<PurchaseSnack> snacks = purchaseSnackRepository.findByCustomerIdAndPaid(user.getId(), false);

        if (seats.isEmpty()) {
            model.addAttribute("error", "No tienes asientos reservados sin pagar.");
            return "User/checkout"; // O muestra un mensaje informativo
        }

        Optional<Booking> bookingOpt = bookingRepository.findById(seats.get(0).getBookingId());
        if (bookingOpt.isEmpty()) {
            model.addAttribute("error", "No se encontró la reserva asociada a los asientos.");
            return "User/checkout";
        }

        Booking booking = bookingOpt.get();
        Optional<Showing> showingOpt = showingRepository.findById(booking.getShowingId());
        if (showingOpt.isEmpty()) {
            model.addAttribute("error", "No se encontró la función asociada.");
            return "User/checkout";
        }

        Showing showing = showingOpt.get();

        double bookingCost = seats.size() * showing.getTicketPrice();
        double snackCost = snacks.stream()
                .mapToDouble(snack -> snack.getQuantity() * snackRepository.findById(snack.getSnackId()).orElse(new Snack()).getPrice())
                .sum();
        double totalCost = bookingCost + snackCost;

        snacks.forEach(snack -> snack.setName(snackRepository.findById(snack.getSnackId()).get().getName()));
        snacks.forEach(snack -> snack.setPrice(snackRepository.findById(snack.getSnackId()).get().getPrice()));
        snacks.forEach(snack -> snack.setTotalPrice(snackRepository.findById(snack.getSnackId()).get().getPrice()*snack.getQuantity()));

        seats.forEach(seat -> seat.setPrice(showingRepository.findById(bookingRepository.findById(seat.getBookingId()).get().getShowingId()).get().getTicketPrice()));


        seatRepository.saveAll(seats);
        purchaseSnackRepository.saveAll(snacks);

        model.addAttribute("user", user);
        model.addAttribute("booking", booking);
        model.addAttribute("seats", seats);
        model.addAttribute("snacks", snacks);
        model.addAttribute("bookingCost", bookingCost);
        model.addAttribute("snackCost", snackCost);
        model.addAttribute("totalCost", totalCost);

        return "User/checkout";
    }


    @PostMapping("/confirm")
    @PreAuthorize("hasRole('USER')")
    public String confirmCheckout(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WebUser user = userRepository.findByEmail(authentication.getName()).orElse(null);

        List<Seat> seats = seatRepository.getSeatsByUserIdAndPaid(user.getId(), false);
        List<PurchaseSnack> snacks = purchaseSnackRepository.findByCustomerIdAndPaid(user.getId(), false);

        seats.forEach(seat -> seat.setPaid(true));
        snacks.forEach(snack -> snack.setPaid(true));
        LocalDateTime hour = LocalDateTime.now();
        seats.forEach(seat -> seat.setBookingDate(hour));
        snacks.forEach(snack -> snack.setBookingDate(hour));
        seatRepository.saveAll(seats);
        purchaseSnackRepository.saveAll(snacks);
        return "redirect:/api/users/menu";
    }


    @PostMapping("/cancel")
    @PreAuthorize("hasRole('USER')")
    public String cancelCheckout(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WebUser user = userRepository.findByEmail(authentication.getName()).orElse(null);

        List<Seat> seats = seatRepository.getSeatsByUserIdAndPaid(user.getId(), false);
        List<PurchaseSnack> snacks = purchaseSnackRepository.findByCustomerIdAndPaid(user.getId(), false);

        List<Long> seatsIds = seatRepository.findIdsByUserIdAndPaid(user.getId(), false);
        List<Long> snackIds = purchaseSnackRepository.findIdsByCustomerIdAndPaid(user.getId(), false);

        if (!seats.isEmpty()) {
            seatServices.cancelSeats(seats.get(0).getBookingId(), seatsIds, user.getId());
        }
        if (!snacks.isEmpty()) {
            purchaseSnackServices.cancelPurchases(snacks.get(0).getShowingId(), snackIds, user.getId());
        }

        return "redirect:/api/users/menu";
    }


}

