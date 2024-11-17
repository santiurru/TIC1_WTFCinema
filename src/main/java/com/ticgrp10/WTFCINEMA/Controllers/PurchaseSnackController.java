package com.ticgrp10.WTFCINEMA.Controllers;

import com.ticgrp10.WTFCINEMA.Entities.*;
import com.ticgrp10.WTFCINEMA.Repositories.BookingRepository;
import com.ticgrp10.WTFCINEMA.Repositories.SeatRepository;
import com.ticgrp10.WTFCINEMA.Repositories.SnackRepository;
import com.ticgrp10.WTFCINEMA.Services.BookingService;
import com.ticgrp10.WTFCINEMA.Services.PurchaseSnackService;
import com.ticgrp10.WTFCINEMA.Services.SnackServices;
import com.ticgrp10.WTFCINEMA.Services.WebUserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/purchase/snack")
public class PurchaseSnackController {

    @Autowired
    SnackServices snackServices;

    @Autowired
    SnackRepository snackRepository;

    @Autowired
    PurchaseSnackService purchaseSnackServices;

    @Autowired
    WebUserServices userServices;
    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    SeatRepository seatRepository;

    // todo ?????????????????????????????????????????????????
    @GetMapping("/home/user")
    @PreAuthorize("hasRole('USER')")
    public String home() {return "User/user";} //????

    //purchase
    @GetMapping("/create")
    @PreAuthorize("hasRole('USER')")
    public String createSnackPurchaseForm(Model model) {
        model.addAttribute("snacks", snackRepository.findAll());
        model.addAttribute("user", getCurrentUser());
        return "Snacks/createSnackPurchase";
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    public String createSnackPurchase(@RequestParam List<Long> snackIds,
                                      @RequestParam List<Integer> snackQuantities,
                                      Model model) {

        WebUser currentUser = getCurrentUser();

        if (snackIds.size() != snackQuantities.size()) {
            model.addAttribute("error", "Las listas de snacks y cantidades no coinciden.");
            return "Snacks/createSnackPurchase";
        }

        for (int i = 0; i < snackIds.size(); i++) {
            if (snackQuantities.get(i) == 0){
                continue;
            }

            Optional<Snack> snackOptional = snackRepository.findById(snackIds.get(i));

            if (!snackOptional.isPresent()) {
                model.addAttribute("error", "El snack con ID " + snackIds.get(i) + " no se encuentra disponible.");
                return "Snacks/createSnackPurchase";
            }

            Snack snack = snackOptional.get();
            int quantity = snackQuantities.get(i);

            PurchaseSnack purchaseSnack = new PurchaseSnack();
            purchaseSnack.setSnackId(snack.getId());
            purchaseSnack.setCustomerId(currentUser.getId());
            purchaseSnack.setQuantity(quantity);
            purchaseSnack.setPaid(false);
            List<Seat> seats = seatRepository.getSeatsByUserIdAndPaid(currentUser.getId(), false);
            if (seats.size()>0){
                Booking booking = bookingRepository.findById(seats.get(0).getBookingId()).get();
                purchaseSnack.setShowingId(booking.getShowingId());
            }

            purchaseSnackServices.addPurchaseSnack(purchaseSnack);
        }

        return "redirect:/api/checkout";
    }

    @GetMapping("/list")
    @PreAuthorize("hasRole('USER')")
    public String listSnackPurchases(Model model) {
        List<PurchaseSnack> purchaseSnacks = purchaseSnackServices.getUserPurchases(getCurrentUser().getId());
        model.addAttribute("snacksPurchases", purchaseSnacks);
        return "Snacks/mySnacksPurchases";
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
