package com.ticgrp10.WTFCINEMA.Services;

import com.ticgrp10.WTFCINEMA.Entities.Movie;
import com.ticgrp10.WTFCINEMA.Entities.Snack;
import com.ticgrp10.WTFCINEMA.Exceptions.InvalidDataException;
import com.ticgrp10.WTFCINEMA.Repositories.SnackRepository;
import jakarta.persistence.EntityExistsException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SnackServices {

    @Autowired
    private SnackRepository snackRepository;

    public Snack addSnack(Snack snack) {
        if (snackRepository.findById(snack.getId()).isPresent()) {
            throw new IllegalArgumentException("El snack ya existe");
        }
        if (snack.getName() == null || snack.getName().isEmpty()) {
            throw new IllegalArgumentException("El nombre del snack no puede estar vacio");
        }
        if (snack.getPrice() <= 0) {
            throw new IllegalArgumentException("El precio del snack es invalido");
        }
        return snackRepository.save(snack);
    }

    public List<Snack> getAll() {
        return snackRepository.findAll();
    }
}
