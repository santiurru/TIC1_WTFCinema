package com.ticgrp10.WTFCINEMA.Controllers;

import com.ticgrp10.WTFCINEMA.Entities.Admin;
import com.ticgrp10.WTFCINEMA.Services.AdminServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminWebController {

    @Autowired
    private AdminServices adminServices;



    @GetMapping("/home")
    public String adminHome() {
        return "Admin/admin"; // Devuelve la vista del menu de admin
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
        return "Admin/moviesManagement";
    }

    @GetMapping("/movies/list")
    public String listMovies() {
        return "redirect:/movie/list";
    }

    @GetMapping("/movies/create")
    public String createMovieForm() {
        return "redirect:/movie/create";
    }

    @GetMapping("/showings")
    public String indexShowings() {
        return "Showings/moviesShowings";
    }

    @GetMapping("/showings/create")
    public String createShowingForm() {
        return "redirect:/showing/create";
    }

//    @PostMapping("/showings/create")
//    public String createShowing(Showing showing) {
//        showingServices.addShowing(showing); // Guarda el showing
//        return "redirect:/admin/showings/list"; // Redirige a la lista de showings
//    }
//
//    // Lista de showings por película
//    @GetMapping("/showings/list")
//    public String listShowingsByMovie(@RequestParam Long movieId, Model model) {
//        List<Showing> showings = showingServices.getShowingsByMovie(movieId);
//        model.addAttribute("showings", showings); // Agrega los showings al modelo
//        return "listShowings"; // Vista que muestra la lista de showings por película
//    }


    @GetMapping("/snacks")
    public String indexSnacks() {
        return "Snacks/snacks";
    }

    @GetMapping("/snacks/list")
    public String listSnacks() {
        return "redirect:/snacks/list";
    }

    @GetMapping("/snacks/create")
    public String createSnackForm() {
        return "redirect:/snacks/create";
    }

}

