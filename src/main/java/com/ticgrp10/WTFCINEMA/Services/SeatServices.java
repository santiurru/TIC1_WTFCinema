package com.ticgrp10.WTFCINEMA.Services;

import com.ticgrp10.WTFCINEMA.Entities.Booking;
import com.ticgrp10.WTFCINEMA.Entities.Seat;
import com.ticgrp10.WTFCINEMA.Repositories.BookingRepository;
import com.ticgrp10.WTFCINEMA.Repositories.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SeatServices {

    @Autowired
    SeatRepository seatRepo;

    @Autowired
    BookingRepository bookingRepository;

    public List<Seat> getSeatsByShowing(Long showingId){
        return seatRepo.getSeatsByShowingId(showingId);
    }
    public List<Seat> getSeatsByBookingId(Long bookingId){
        return seatRepo.getSeatsByBookingId(bookingId);
    }

    public boolean cancelSeats(Long bookingId, List<Long> seatIds, Long customerId) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Reserva no encontrada."));

        if (booking.getCustomerId() != (customerId)) {
            throw new SecurityException("Acceso no autorizado.");
        }

        List<Seat> seats = seatRepo.findAllById(seatIds);
        seats.forEach(seat -> {
            if (!seat.getBookingId().equals(bookingId)) {
                throw new IllegalArgumentException("El asiento no pertenece a esta reserva.");
            }
            seatRepo.delete(seat); // Desasocia el asiento
        });
        return true;
    }

    public Seat bookSeat(Long bookingId, int row, int column){
        Optional<Seat> seatOptional = seatRepo.findBySeatColumnAndSeatRowAndBookingId(column, row, bookingId);
        if (seatOptional.isPresent()){
            throw new IllegalArgumentException("El asiento ya está ocupado");
        }


        Seat seat = new Seat();
        seat.setBookingId(bookingId);
        seat.setSeatColumn(column);
        seat.setSeatRow(row);
        seat.setPaid(false);

        return seatRepo.save(seat);
    }



}
