package com.ticgrp10.WTFCINEMA.Services;


import com.ticgrp10.WTFCINEMA.Entities.PurchaseSnack;
import com.ticgrp10.WTFCINEMA.Repositories.PurchaseSnackRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PurchaseSnackService {

    @Autowired
    PurchaseSnackRepository purchaseSnackRepository;

    public PurchaseSnack addPurchaseSnack(PurchaseSnack snack) {
        return purchaseSnackRepository.save(snack);
    }
}