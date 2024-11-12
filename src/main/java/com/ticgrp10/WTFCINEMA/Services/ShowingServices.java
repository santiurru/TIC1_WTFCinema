package com.ticgrp10.WTFCINEMA.Services;

import com.ticgrp10.WTFCINEMA.Entities.*;
import com.ticgrp10.WTFCINEMA.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.hibernate.grammars.hql.HqlParser.ROWS;

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

    public Map<String, String> getSeatAvailability(Long showingId) {
        List<Integer> ocuppiedSeats = notAvailableSeats(showingId);

        Map<String, String> seatMap = new HashMap<>();

        int seatNumber = 1;
        for (int row = 1; row <= ROWS; row++) {
            for (int col = 1; col <= COLUMNS; col++) {
                String position = row + "," + col;
                if (ocuppiedSeats.contains(seatNumber)) {
                    seatMap.put(position, "occupied");
                } else {
                    seatMap.put(position, "available");
                }
                seatNumber++;
            }
        }

        return seatMap;
    }


    public List<Theatre> getTheatersByMovie(Long movieId) {
        return theatreRepository.findTheatresByMovieId(movieId);
    }

    public List<LocalDateTime> getDaysByMovieAndTheater(Long movieId, Long theaterId) {
        // Encuentra funciones de la película en el cine específico y extrae las fechas únicas
        List<Showing> showings = showingRepository.findShowingsByMovieIdAndTheatreId(movieId, theaterId);
        return showings.stream()
                .map(Showing::getDate)
                .distinct()
                .collect(Collectors.toList());
    }

    public List<Showing> getShowingsByMovieTheaterAndDay(Long movieId, Long theaterId, String day) {
        return showingRepository.findShowingsByMovieIdAndTheaterIdAndDay(movieId, theaterId, day);
    }
}

