package com.ticgrp10.WTFCINEMA.Services;

import com.ticgrp10.WTFCINEMA.Entities.*;
import com.ticgrp10.WTFCINEMA.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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

//        Optional<Theatre> theatreOptional = theatreRepository.findByRoomId(showing.getRoomId());
//        if (!theatreOptional.isPresent()){
//            throw new IllegalArgumentException("El cine no existe");
//        }

        Optional<Room> roomOptional = roomRepository.findById(showing.getRoomId());
        if (!roomOptional.isPresent()) {
            throw new IllegalArgumentException("La sala no existe");
        }

        return showingRepository.save(showing);
    }

    public List<Showing> getAll() {
        return showingRepository.findAll();
    }

    // Método para verificar si la sala está libre en el horario
    public boolean isRoomAvailable(long roomId, LocalDateTime date) {
        List<Showing> showings = showingRepository.findByRoomIdAndDate(roomId, date);
        return showings.isEmpty(); // Si no hay ningún showing en ese horario, la sala está disponible
    }

//    // Método para reservar un asiento
//    public boolean reservarAsiento(Long showingId, int fila, int columna) {
//        Optional<Showing> showingOpt = showingRepository.findById(showingId);
//        if (showingOpt.isPresent()) {
//            Showing showing = showingOpt.get();
//            if (showing.getSeatAvailability()[fila][columna]) {
//                showing.getSeatAvailability()[fila][columna] = false;
//                showingRepository.save(showing);
//                return true;
//            }
//        }
//        return false;
//    }
//
//    // Método para verificar disponibilidad
//    public boolean isAvailable(Long showingId, int fila, int columna) {
//        Optional<Showing> showingOpt = showingRepository.findById(showingId);
//        return showingOpt.map(showing -> showing.getSeatAvailability()[fila][columna])
//                .orElse(false);
//    }
}
