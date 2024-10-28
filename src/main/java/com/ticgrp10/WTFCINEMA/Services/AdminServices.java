package com.ticgrp10.WTFCINEMA.Services;

import com.ticgrp10.WTFCINEMA.Entities.*;
import com.ticgrp10.WTFCINEMA.Repositories.AdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServices {

    @Autowired
    private AdminRepository adminRepository;

    @Autowired
    private MovieServices movieServices;

    @Autowired
    private SnackServices snackServices;

    @Autowired
    private ShowingServices showingServices;

//    @Autowired
//    private BCryptPasswordEncoder bCryptPasswordEncoder;

    public Admin addAdmin(Admin admin) {
        if (admin.getName() == null || admin.getName().isEmpty() || admin.getSurname() == null || admin.getSurname().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (adminRepository.findByEmail(admin.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un usuario con ese correo electrónico");
        }

        if (!isValidEmail(admin.getEmail())) {
            throw new IllegalArgumentException("El formato del correo electrónico no es válido");
        }

//        String encodedPassword = bCryptPasswordEncoder.encode(admin.getPassword());
//        admin.setPassword(encodedPassword);

        return adminRepository.save(admin);
    }

    public Optional<Admin> findByEmail(String email) {
        return adminRepository.findByEmail(email);
    }

    public Optional<Admin> findById(Long id) {
        return adminRepository.findById(id);
    }

    public List<Admin> getAll()
    {
        return adminRepository.findAll();
    }

    public Movie addMovie(Movie movie) {
        return movieServices.addMovie(movie);
    }

    public Snack addSnack(Snack snack) {
        return snackServices.addSnack(snack);
    }

    public Showing addShowing(Showing showing){
        return showingServices.addShowing(showing);
    }


    private boolean isValidEmail(String email) {
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return email.matches(emailRegex);
    }
}
