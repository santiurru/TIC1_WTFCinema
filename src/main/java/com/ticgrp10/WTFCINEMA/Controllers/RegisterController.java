package com.ticgrp10.WTFCINEMA.Controllers;


import com.ticgrp10.WTFCINEMA.Entities.WebUser;
import com.ticgrp10.WTFCINEMA.Services.WebUserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
class RegisterController {

    @Autowired
    private WebUserServices userService;

    @GetMapping("/register")
    public String registerForm(Model model) {
        model.addAttribute("user", new WebUser());
        return "register";
    }

    @PostMapping("/register")
    public String register(WebUser user) {
        userService.addUser(user);
        return "redirect:/login";
    }
}

