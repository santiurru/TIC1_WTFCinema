package com.ticgrp10.WTFCINEMA.Services;

import com.ticgrp10.WTFCINEMA.Entities.*;
import com.ticgrp10.WTFCINEMA.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShowingServices {

    @Autowired
    ShowingRepository showingRepository;

    @Autowired
    MovieRepository movieRepository;

    @Autowired
    RoomRepository roomRepository;

    @Autowired
    TheatreRepository theatreRepository;

    public Showing addShowing(Showing showing) {
        if (showingRepository.existsById(showing.getId())) {
            throw new IllegalArgumentException("La funcion ya existe");
        }
        Optional<Movie> movieOptional = movieRepository.findById(showing.getMovieId());
        if (!movieOptional.isPresent()){
            throw new IllegalArgumentException("La pelicula no existe");
        }
        Optional<Theatre> theatreOptional = theatreRepository.findById(showing.getMovieId());
        if (!theatreOptional.isPresent()){
            throw new IllegalArgumentException("El cine no existe");
        }
        Optional<Room> roomOptional = roomRepository.findByIdAndTheatreId(showing.getRoomId(), theatreOptional.get().getTheatre_id());
        if (!roomOptional.isPresent()) {
            throw new IllegalArgumentException("La sala no existe en el cine seleccionado.");
        }

        return showingRepository.save(showing);
    }

    public List<Showing> getAll() {
        return showingRepository.findAll();
    }
}
