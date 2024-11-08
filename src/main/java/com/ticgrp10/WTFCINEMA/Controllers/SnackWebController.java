package com.ticgrp10.WTFCINEMA.Controllers;

import com.ticgrp10.WTFCINEMA.Entities.Snack;
import com.ticgrp10.WTFCINEMA.Services.SnackServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/snacks")
public class SnackWebController {

    @Autowired
    private SnackServices snackServices;

    @GetMapping("/home/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String home(Model model, @ModelAttribute ("messageOK" ) String messageOK) {
        model.addAttribute("messageOK", messageOK);
        return "Admin/admin";
    }

    @GetMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public String createSnackForm(Model model) {
        model.addAttribute("snack", new Snack());
        return "Snacks/createSnack";
    }

    @PostMapping("/create")
    @PreAuthorize("hasRole('ADMIN')")
    public String createSnack(Snack snack, RedirectAttributes redirectAttributes) {
        snackServices.addSnack(snack);
        redirectAttributes.addFlashAttribute("messageOK", "Snack creado");
        return "redirect:/snacks/home/admin";
    }

    @GetMapping("/list")
    public String listSnacks(Model model) {
        model.addAttribute("snacks", snackServices.getAll());
        return "Snacks/listSnacks";
    }
}
