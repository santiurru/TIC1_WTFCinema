package com.ticgrp10.WTFCINEMA.Controllers;

import com.ticgrp10.WTFCINEMA.Entities.*;
import com.ticgrp10.WTFCINEMA.Repositories.*;
import com.ticgrp10.WTFCINEMA.Services.BookingService;
import com.ticgrp10.WTFCINEMA.Services.PurchaseSnackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public String checkoutPage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        WebUser user = userRepository.findByEmail(authentication.getName()).orElse(null);

        if (user == null) {
            return "redirect:/login"; // Redirigir si el usuario no está autenticado
        }

        // Verificar tarjeta de crédito
        if (user.getCardNumber() == 0) {
            return "redirect:/api/users/creditCard"; // Redirigir para agregar tarjeta
        }

        // Obtener reservas y snacks asociados no pagados
        List<Seat> seats = seatRepository.getSeatsByUserIdAndPaid(user.getId(), false);
        List<PurchaseSnack> snacks = purchaseSnackRepository.findByCustomerIdAndPaid(user.getId(), false);

        if (seats.isEmpty()) {
            model.addAttribute("error", "No tienes asientos reservados sin pagar.");
            return "User/checkout"; // O muestra un mensaje informativo
        }

        // Obtener booking y showing
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

        // Calcular costos
        double bookingCost = seats.size() * showing.getTicketPrice();
        double snackCost = snacks.stream()
                .mapToDouble(snack -> snack.getQuantity() * snackRepository.findById(snack.getSnackId()).orElse(new Snack()).getPrice())
                .sum();
        double totalCost = bookingCost + snackCost;

        // Marcar asientos y snacks como pagados
        seats.forEach(seat -> seat.setPaid(true));
        snacks.forEach(snack -> snack.setPaid(true));
        snacks.forEach(snack -> snack.setName(snackRepository.findById(snack.getSnackId()).get().getName()));
        snacks.forEach(snack -> snack.setPrice(snackRepository.findById(snack.getSnackId()).get().getPrice()));


        // Guardar cambios en la base de datos
        seatRepository.saveAll(seats);
        purchaseSnackRepository.saveAll(snacks);

        // Pasar datos al modelo
        model.addAttribute("user", user);
        model.addAttribute("booking", booking);
        model.addAttribute("seats", seats);
        model.addAttribute("snacks", snacks);
        model.addAttribute("bookingCost", bookingCost);
        model.addAttribute("snackCost", snackCost);
        model.addAttribute("totalCost", totalCost);

        return "User/checkout";
    }
}

