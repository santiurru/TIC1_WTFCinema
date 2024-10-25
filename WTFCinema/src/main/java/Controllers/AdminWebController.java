package Controllers;

import Entities.Admin;
import Entities.Movie;
import Services.AdminServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminWebController {

    @Autowired
    private AdminServices adminServices;

    @GetMapping("/")
    public String adminHome() {
        return "adminHome"; // Devuelve la vista de admin.html
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
    public String listMovies(Model model) {
//        model.addAttribute("movies", adminServices.getAllMovies());
        return "listMovies"; // Asegúrate de que listMovies.html existe en templates
    }

    @GetMapping("/movies/create")
    public String createMovieForm(Model model) {
        model.addAttribute("movie", new Movie());
        return "createMovie"; // Asegúrate de que createMovie.html existe en templates
    }

    @PostMapping("/movies/create")
    public String createMovie(Movie movie) {
        adminServices.addMovie(movie);
        return "redirect:/admin/movies"; // Redirige a la lista de películas
    }
}

