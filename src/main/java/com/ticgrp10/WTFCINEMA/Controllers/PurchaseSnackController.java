package com.ticgrp10.WTFCINEMA.Controllers;

import com.ticgrp10.WTFCINEMA.Entities.PurchaseSnack;
import com.ticgrp10.WTFCINEMA.Entities.Snack;
import com.ticgrp10.WTFCINEMA.Entities.WebUser;
import com.ticgrp10.WTFCINEMA.Repositories.SnackRepository;
import com.ticgrp10.WTFCINEMA.Services.PurchaseSnackService;
import com.ticgrp10.WTFCINEMA.Services.SnackServices;
import com.ticgrp10.WTFCINEMA.Services.WebUserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/home/user")
    @PreAuthorize("hasRole('USER')")
    public String home() {return "User/user";} //????

    //purchase
    @GetMapping("/create")
    @PreAuthorize("hasRole('USER')")
    public String createSnackPurchaseForm(Model model) {
        model.addAttribute("snacks", purchaseSnackServices.getAll());
        model.addAttribute("user", getCurrentUser());
        return "Snack/createSnackPurchase";
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('USER')")
    public String createSnackPurchase(@RequestParam List<Long> snackIds,
                                @RequestParam List<Integer> snackQuantities,
                                @RequestParam WebUser currentUser){

        for (int i = 0; i < snackIds.size(); i++) {
            Optional<Snack> snackOptional = snackRepository.findById(snackIds.get(i));
            Snack snack = snackOptional.get();
            int quantity = snackQuantities.get(i);
            PurchaseSnack purchaseSnack = new PurchaseSnack();
            purchaseSnack.setSnackId(snack.getId());
            purchaseSnack.setCustomerId(currentUser.getId());
            purchaseSnack.setQuantity(quantity);
            purchaseSnackServices.addPurchaseSnack(purchaseSnack);
        }
        return "redirect:/purchase/snack/home/user";
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
