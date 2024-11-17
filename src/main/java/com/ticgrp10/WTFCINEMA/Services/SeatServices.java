package com.ticgrp10.WTFCINEMA.Services;

import com.ticgrp10.WTFCINEMA.Entities.Booking;
import com.ticgrp10.WTFCINEMA.Entities.Seat;
import com.ticgrp10.WTFCINEMA.Repositories.BookingRepository;
import com.ticgrp10.WTFCINEMA.Repositories.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SeatServices {

    @Autowired
    SeatRepository seatRepo;

    @Autowired
    BookingRepository bookingRepository;

    public List<Seat> getSeatsByShowing(Long showingId){
        return seatRepo.getSeatsByShowingId(showingId);
    }
//    public List<String> getSeatsByShowingAux(Long showingId){
//        return seatRepo.getSeatsByShowingIdAux(showingId);
//    }

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
            seatRepo.delete(seat); // Desasociar asiento
        });

//        seatRepo.saveAll(seats);
        return true;
    }

    public Seat bookSeat(Long bookingId, int row, int column){
        Seat seat = new Seat();
        seat.setBookingId(bookingId);
        seat.setSeatColumn(column);
        seat.setSeatRow(row);
        seat.setPaid(false);
        seat.setBookingDate(LocalDate.now());

        return seatRepo.save(seat);
    }



}
