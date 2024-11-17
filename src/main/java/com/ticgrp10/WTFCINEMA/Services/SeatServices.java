package com.ticgrp10.WTFCINEMA.Services;

import com.ticgrp10.WTFCINEMA.Entities.Seat;
import com.ticgrp10.WTFCINEMA.Repositories.SeatRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeatServices {

    @Autowired
    SeatRepository seatRepo;

    public List<Seat> getSeatsByShowing(Long showingId){
        return seatRepo.getSeatsByShowingId(showingId);
    }

    public Seat bookSeat(Long bookingId, int row, int column){
        Seat seat = new Seat();
        seat.setBookingId(bookingId);
        seat.setSeatColumn(column);
        seat.setSeatRow(row);

        return seatRepo.save(seat);
    }



}
