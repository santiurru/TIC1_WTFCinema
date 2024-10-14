package Controllers;

import Entities.WebUser;
import Services.WebUserServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/users")
public class WebUserController {
    @Autowired
    private WebUserServices appUserService;

    @PostMapping("/register")
    public ResponseEntity<WebUser> registerUser(@RequestBody  WebUser appUser) { //@Valid no anda
        if (appUserService.findByEmail(appUser.getEmail()).isPresent()) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(null); // Usuario ya existe
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(appUserService.registerUser(appUser));
    }

    @GetMapping("/login")
    public ResponseEntity<WebUser> login(@RequestParam String email, @RequestParam String password) {
        Optional<WebUser> user = appUserService.findByEmail(email);
        if (user.isPresent() && user.get().getPassword().equals(password)) {
            return ResponseEntity.ok(user.get());
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
    }

}
