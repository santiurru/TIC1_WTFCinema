package com.ticgrp10.WTFCINEMA.Controllers;

import com.ticgrp10.WTFCINEMA.Entities.Admin;
import com.ticgrp10.WTFCINEMA.Entities.Movie;
import com.ticgrp10.WTFCINEMA.Entities.Showing;
import com.ticgrp10.WTFCINEMA.Entities.Snack;
import com.ticgrp10.WTFCINEMA.Services.AdminServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/admin")
public class AdminRestController {

    @Autowired
    private AdminServices adminServices;

    @GetMapping("/")
    public ResponseEntity<List<Admin>> getAll() {
        return ResponseEntity.ok(adminServices.getAll());
    }

    @PostMapping("/movie")
    public ResponseEntity<Movie> addMovie(@RequestBody Movie movie) {
        Movie addedMovie = adminServices.addMovie(movie);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedMovie);
    }

    @PostMapping("/snack")
    public ResponseEntity<Snack> addSnack(@RequestBody Snack snack) {
        Snack addedSnack = adminServices.addSnack(snack);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedSnack);
    }

    @PostMapping("/showing")
    public ResponseEntity<Showing> addShowing(@RequestBody Showing showing) {
        Showing addedShowing = adminServices.addShowing(showing);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedShowing);
    }

    @PostMapping("/admin")
    public ResponseEntity<Admin> addAdmin(@RequestBody Admin admin) {
        Admin addedAdmin = adminServices.addAdmin(admin);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedAdmin);
    }
}
