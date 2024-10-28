package com.ticgrp10.WTFCINEMA.Controllers;

import com.ticgrp10.WTFCINEMA.Entities.Snack;
import com.ticgrp10.WTFCINEMA.Services.SnackServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/snack")
public class SnackWebController {

    @Autowired
    private SnackServices snackServices;

    @GetMapping("/create")
    public String createSnackForm(Model model) {
        model.addAttribute("snack", new Snack());
        return "createSnack";
    }

    @PostMapping("/create")
    public String createSnack(Snack snack) {
        snackServices.addSnack(snack);
        return "redirect:/snack/list";
    }

    @GetMapping("/list")
    public String listSnacks(Model model) {
        model.addAttribute("snacks", snackServices.getAll());
        return "listSnacks";
    }
}
