//package com.ticgrp10.WTFCINEMA.Controllers;
//
//import com.ticgrp10.WTFCINEMA.Entities.PurchaseSnack;
//import com.ticgrp10.WTFCINEMA.Entities.Seat;
//import com.ticgrp10.WTFCINEMA.Entities.WebUser;
//import com.ticgrp10.WTFCINEMA.Repositories.PurchaseSnackRepository;
//import com.ticgrp10.WTFCINEMA.Repositories.SeatRepository;
//import com.ticgrp10.WTFCINEMA.Repositories.UserRepository;
//import com.ticgrp10.WTFCINEMA.Services.BookingService;
//import com.ticgrp10.WTFCINEMA.Services.PurchaseSnackService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.ui.Model;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("/api/checkout")
//public class CheckoutController {
//
//    @Autowired
//    private BookingService bookingService;
//    @Autowired
//    private PurchaseSnackService snackPurchaseService;
//
//    @Autowired
//    private UserRepository userRepository;
//    @Autowired
//    private SeatRepository seatRepository;
//    @Autowired
//    private PurchaseSnackRepository purchaseSnackRepository;
//
//    @GetMapping("/checkout")
//    public String checkoutPage(Model model) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        WebUser user = userRepository.findByEmail(authentication.getName()).get();
//
//        // Verificar tarjeta
//        if (user.getCardNumber() == null) {
//            return "redirect:/api/users/creditCard"; // Redirigir para que agregue la tarjeta
//        }
//
//        // Obtener reservas y snacks asociados
//        List<Seat> seats = seatRepository.getSeatsByUserIdAndPaid(user.getId(), false);
//        List<PurchaseSnack> snacks = purchaseSnackRepository.getPurchaseSnackByCustomerIdAndAndPaid();
//
//
//        // Calcular costos
//        double bookingCost = userBookings.size() * SEAT_PRICE; // Precio por asiento
//        double snackCost = userSnacks.stream()
//                .mapToDouble(snack -> snack.getQuantity() * snack.getPrice())
//                .sum();
//        double totalCost = bookingCost + snackCost;
//
//        // Pasar datos a la vista
//        model.addAttribute("user", user);
//        model.addAttribute("bookings", userBookings);
//        model.addAttribute("snacks", userSnacks);
//        model.addAttribute("bookingCost", bookingCost);
//        model.addAttribute("snackCost", snackCost);
//        model.addAttribute("totalCost", totalCost);
//
//        return "User/checkout";
//    }
//
//}
//
