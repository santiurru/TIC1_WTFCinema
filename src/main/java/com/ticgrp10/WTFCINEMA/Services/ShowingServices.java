package com.ticgrp10.WTFCINEMA.Services;

import com.ticgrp10.WTFCINEMA.Entities.*;
import com.ticgrp10.WTFCINEMA.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
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

    @Autowired
    BookingService bookingServices;

    public Showing addShowing(Showing showing) {
        if (showingRepository.existsById(showing.getId())) {
            throw new IllegalArgumentException("La funcion ya existe");
        }
        Optional<Movie> movieOptional = movieRepository.findById(showing.getMovieId());
        if (!movieOptional.isPresent()){
            throw new IllegalArgumentException("La pelicula no existe");
        }

        Optional<Theatre> theatreOptional = theatreRepository.findByRoomId(showing.getRoomId());
        if (!theatreOptional.isPresent()){
            throw new IllegalArgumentException("El cine no existe");
        }

        Optional<Room> roomOptional = roomRepository.findById(showing.getRoomId());
        if (!roomOptional.isPresent()) {
            throw new IllegalArgumentException("La sala no existe");
        }

        return showingRepository.save(showing);
    }

    public List<Showing> getAll() {
        return showingRepository.findAll();
    }

    //verificar si la sala está libre en el horario
    public boolean isRoomAvailable(long roomId, LocalDateTime date) {
        List<Showing> showings = showingRepository.findByRoomIdAndDate(roomId, date);
        return showings.isEmpty();
    }


    //verificar si un asiento está disponible
    public List<Integer> notAvailableSeats(Long showingId) {
        Optional<Showing> showingOpt = showingRepository.findById(showingId);
        if (showingOpt.isPresent()) {
            Showing showing = showingOpt.get();
            List<Booking> bookingsList = bookingServices.getShowingBookings(showing);
            List<Integer> ocuppiedSeats = new ArrayList<>();

            for (Booking variable : bookingsList){
                ocuppiedSeats.addLast(variable.getSeatId());
            }

            return ocuppiedSeats;
        }
        throw new IllegalArgumentException("La función no existe.");
    }

//    //reservar un asiento
//    public void reserveSeat(Long showingId, int seat) {
//        Optional<Showing> showingOpt = showingRepository.findById(showingId);
//        if (showingOpt.isPresent()) {
//            Showing showing = showingOpt.get();
//
//            if (!notAvailableSeats(showingId).contains(seat)) {
//                showingRepository.save(showing);
//            } else {
//                throw new IllegalArgumentException("El asiento ya está ocupado.");
//            }
//        } else {
//            throw new IllegalArgumentException("La función no existe.");
//        }
//    }

//    // liberar un asiento
//    public void releaseSeat(Long showingId, int seat) {
//        Optional<Showing> showingOpt = showingRepository.findById(showingId);
//        if (showingOpt.isPresent()) {
//            Showing showing = showingOpt.get();
//
//            showingRepository.save(showing);
//        } else {
//            throw new IllegalArgumentException("La función no existe.");
//        }
//    }
}
