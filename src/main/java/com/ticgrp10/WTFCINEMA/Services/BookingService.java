package com.ticgrp10.WTFCINEMA.Services;

import com.ticgrp10.WTFCINEMA.Entities.Booking;
import com.ticgrp10.WTFCINEMA.Entities.Showing;
import com.ticgrp10.WTFCINEMA.Entities.WebUser;
import com.ticgrp10.WTFCINEMA.Repositories.BookingRepository;
import com.ticgrp10.WTFCINEMA.Repositories.SeatRepository;
import com.ticgrp10.WTFCINEMA.Repositories.ShowingRepository;
import com.ticgrp10.WTFCINEMA.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository webUserRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private ShowingRepository showingRepository;

    public Booking addBooking(Booking booking) {
        Optional<WebUser> userOptional = webUserRepository.findById(booking.getCustomerId());
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("Customer not found");
        }

//        Optional<Seat> seatOpt = seatRepository.findByRoomAndRowAndNumber(booking.getSeatId().getRoom(), booking.getSeat().getRow(), booking.getSeat().getNumber());
//        if (seatOpt.isEmpty()) {
//            throw new IllegalArgumentException("Seat not found");
//        }

        Optional<Showing> showingOpt = showingRepository.findById(booking.getShowingId());
        if (showingOpt.isEmpty()) {
            throw new IllegalArgumentException("Showing not found");
        }

        return bookingRepository.save(booking);
    }

    public List<Booking> getBookingsForUser(Long userId) {
        return bookingRepository.findByCustomerId(userId);
    }

    public List<Booking> getBookingsForShowing(Long showingId) {
        return bookingRepository.findByShowingId(showingId);
    }
}
