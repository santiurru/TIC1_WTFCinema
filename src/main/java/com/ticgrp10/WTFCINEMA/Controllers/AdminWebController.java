package com.ticgrp10.WTFCINEMA.Controllers;

import com.ticgrp10.WTFCINEMA.Entities.Admin;
import com.ticgrp10.WTFCINEMA.Entities.Movie;
import com.ticgrp10.WTFCINEMA.Repositories.MovieRepository;
import com.ticgrp10.WTFCINEMA.Services.AdminServices;
import com.ticgrp10.WTFCINEMA.Services.MovieServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminWebController {

    @Autowired
    private AdminServices adminServices;
    @Autowired
    private com.ticgrp10.WTFCINEMA.Repositories.MovieRepository MovieRepository;
    @Autowired
    private MovieServices movieServices;

    @GetMapping("/home")
    public String adminHome() {
        return "admin"; // Devuelve la vista de admin.html
    }

    @GetMapping("/create")
    public String createAdminForm(Model model) {
        model.addAttribute("admin", new Admin()); // Agrega un nuevo objeto Admin al modelo
        return "createAdmin"; // Nombre de la vista para el formulario de creación
    }

    @PostMapping("/create")
    public String createAdmin(Admin admin) {
        adminServices.addAdmin(admin); // Llama al servicio para agregar el administrador
        return "redirect:/admin/list"; // Redirige a la lista de administradores después de la creación
    }

    @GetMapping("/list")
    public String listAdmins(Model model) {
        model.addAttribute("admins", adminServices.getAll()); // Obtiene todos los administradores
        return "listAdmins"; // Nombre de la vista que muestra la lista de administradores
    }

    @GetMapping("/movies")
    public String indexMovies() {
        return "movies";
    }

    @GetMapping("/movies/list")
    public String listMovies(Model model) {
        return "redirect:/movie/list"; // Asegúrate de que listMovies.html existe en templates
    }

    @GetMapping("/movies/create")
    public String createMovieForm(Model model) {
        return "redirect:/movie/create"; // Asegúrate de que createMovie.html existe en templates
    }

}

