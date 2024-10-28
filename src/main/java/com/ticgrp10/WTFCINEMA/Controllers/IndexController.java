package com.ticgrp10.WTFCINEMA.Controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController
{
    @GetMapping("/")
    public String index() {
        return "index1";
    }

    @GetMapping("/error")
    public String error() {return "error1";}

}

