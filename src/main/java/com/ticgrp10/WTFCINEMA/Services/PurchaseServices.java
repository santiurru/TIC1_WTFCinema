package com.ticgrp10.WTFCINEMA.Services;

import com.ticgrp10.WTFCINEMA.Entities.*;
import com.ticgrp10.WTFCINEMA.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PurchaseServices {

    @Autowired
    private PurchaseRepository purchaseRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ShowingRepository showingRepository;

    @Autowired
    private SnackRepository snackRepository;

    @Autowired
    private UserRepository userRepository;

    public Purchase addPurchase(Purchase purchase) {
        Optional<Purchase> purchaseOptional = purchaseRepository.findById(purchase.getId());
        if (purchaseOptional.isPresent()) {
            throw new IllegalArgumentException("La compra ya existe");
        }
        Optional<WebUser> userOptional = userRepository.findById(purchase.getCustomerId());
        if (!userOptional.isPresent()){
            throw new IllegalArgumentException("El usuario no existe");
        }
        if (purchase.getSnacks().isEmpty() && purchase.getBookings().isEmpty()){
            throw new IllegalArgumentException("Compra invalida");
        }
        calculateTotalPrice(purchase);

        return purchaseRepository.save(purchase);
    }

    public double calculateTotalPrice(Purchase purchase) {
        int total = 0;
        for (Booking booking : purchase.getBookings()) {
            total += showingRepository.findById(booking.getShowingId()).get().getTicketPrice();
        }
        for (PurchaseSnack purchaseSnack : purchase.getSnacks()) {
            total += purchaseSnack.getQuantity() * snackRepository.findById(purchaseSnack.getSnackId()).get().getPrice();
        }
        purchase.setTotalPrice(total);
        return total;
    }


}
