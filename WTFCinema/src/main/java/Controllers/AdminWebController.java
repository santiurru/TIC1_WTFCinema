package uy.edu.um.demospring.controllers;

import Entities.Admin;
import Services.AdminServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/admin")
public class AdminWebController {

    @Autowired
    private AdminServices adminServices;

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
}

