package Controllers;

import Entities.Showing;
import Services.ShowingServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;

@Controller
@RequestMapping("/showing")
public class ShowingWebController {

    @Autowired
    private ShowingServices showingServices;

    @GetMapping("/create")
    public String createShowingForm(Model model) {
        model.addAttribute("showing", new Showing());
        return "createShowing";
    }

    @PostMapping("/create")
    public String createShowing(Showing showing) {
        showingServices.addShowing(showing);
        return "redirect:/showing/list";
    }

    @GetMapping("/list")
    public String listShowings(Model model) {
        model.addAttribute("showings", showingServices.getAll());
        return "listShowings";
    }
}

