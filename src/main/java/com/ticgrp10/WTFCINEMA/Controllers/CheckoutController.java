package com.ticgrp10.WTFCINEMA.Controllers;

import com.ticgrp10.WTFCINEMA.Entities.*;
import com.ticgrp10.WTFCINEMA.Repositories.*;
import com.ticgrp10.WTFCINEMA.Services.BookingService;
import com.ticgrp10.WTFCINEMA.Services.PurchaseSnackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
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

    @GetMapping("/checkout")
    public String checkoutPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WebUser user = userRepository.findByEmail(authentication.getName()).get();

        // Verificar tarjeta
        if (user.getCardNumber() == 0) {
            return "redirect:/api/users/creditCard"; // Redirigir para que agregue la tarjeta
        }

        // Obtener reservas y snacks asociados
        List<Seat> seats = seatRepository.getSeatsByUserIdAndPaid(user.getId(), false);
        List<PurchaseSnack> snacks = purchaseSnackRepository.findByCustomerIdAndPaid(user.getId(), false);
        Booking booking = bookingRepository.findById(seats.get(0).getBookingId()).get();
        Showing showing = showingRepository.findById(booking.getShowingId()).get();

        // Calcular costos
        double bookingCost = seats.size() * showing.getTicketPrice();
        float snackCost = 0;

        List<PurchaseSnack> purchaseSnacks = purchaseSnackRepository.findByCustomerIdAndShowingId(user.getId(), showing.getId());
        for (PurchaseSnack purchaseSnack : purchaseSnacks) {
            long gottenSnackId = purchaseSnack.getSnackId();
            purchaseSnack.setPaid(true);
            snackCost += purchaseSnack.getQuantity() * snackRepository.findById(gottenSnackId).get().getPrice();
        }

        double totalCost = bookingCost + snackCost;

        for (Seat seat : seats){
            seat.setPaid(true);
        }


        // Pasar datos a la vista
        model.addAttribute("user", user);
        model.addAttribute("booking", booking);
        model.addAttribute("seats", seats);
        model.addAttribute("snackPurchases", snacks);
        model.addAttribute("bookingCost", bookingCost);
        model.addAttribute("snackCost", snackCost);
        model.addAttribute("totalCost", totalCost);
        return "User/checkout";
    }
}

