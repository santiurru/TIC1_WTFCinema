package com.ticgrp10.WTFCINEMA.Services;


import com.ticgrp10.WTFCINEMA.Entities.Booking;
import com.ticgrp10.WTFCINEMA.Entities.PurchaseSnack;
import com.ticgrp10.WTFCINEMA.Entities.Seat;
import com.ticgrp10.WTFCINEMA.Entities.Showing;
import com.ticgrp10.WTFCINEMA.Repositories.PurchaseSnackRepository;
import com.ticgrp10.WTFCINEMA.Repositories.ShowingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PurchaseSnackService {

    @Autowired
    PurchaseSnackRepository purchaseSnackRepository;

    @Autowired
    ShowingRepository showingRepository;

    public PurchaseSnack addPurchaseSnack(PurchaseSnack snack) {
        return purchaseSnackRepository.save(snack);
    }

    public List<PurchaseSnack> getAll() {return purchaseSnackRepository.findAll();}

    public List<PurchaseSnack> getUserPurchases(long userId){return purchaseSnackRepository.findByCustomerId(userId);}

    public boolean cancelPurchases(Long showingId, List<Long> purchaseSnacks, Long customerId) {
        Showing showing = showingRepository.findById(showingId)
                .orElseThrow(() -> new IllegalArgumentException("Fucion no encontrada"));

        List<PurchaseSnack> snacks = purchaseSnackRepository.findAllById(purchaseSnacks);
        snacks.forEach(snack -> {
            if (snack.getShowingId() != showingId) {
                throw new IllegalArgumentException("El asiento no pertenece a esta reserva.");
            }

            purchaseSnackRepository.delete(snack);
        });
        return true;
    }

}
