package com.ticgrp10.WTFCINEMA.Services;

import com.ticgrp10.WTFCINEMA.Entities.Booking;
import com.ticgrp10.WTFCINEMA.Entities.Showing;
import com.ticgrp10.WTFCINEMA.Entities.WebUser;
import com.ticgrp10.WTFCINEMA.Repositories.BookingRepository;
import com.ticgrp10.WTFCINEMA.Repositories.ShowingRepository;
import com.ticgrp10.WTFCINEMA.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository webUserRepository;

    @Autowired
    private ShowingRepository showingRepository;

    @Autowired
    private ShowingServices showingServices;

    public Booking addBooking(Booking booking) {
        Optional<WebUser> userOptional = webUserRepository.findById(booking.getCustomerId());
        if (userOptional.isEmpty()) {throw new IllegalArgumentException("Customer not found");}

        Optional<Showing> showingOptional = showingRepository.findById(booking.getShowingId());
        if (!showingOptional.isPresent()) {throw new IllegalArgumentException("Showing not found");}
        Showing showing = showingOptional.get();
            return bookingRepository.save(booking);
    }

    public String cancelBooking(WebUser user, Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).get();
        WebUser webUser = webUserRepository.findById(booking.getCustomerId()).get();
        if (!webUser.equals(user)) {
            throw new IllegalStateException("No puedes cancelar una reserva que no te pertenece.");
        }
        bookingRepository.delete(booking);
        return "Reserva cancelada exitosamente.";
    }

    public List<Booking> getBookingsByCustomerId(long customerId) {
        return bookingRepository.findByCustomerIdAndDate(customerId, LocalDateTime.now());
    }



    public List<Booking> getShowingBookings(Showing showing){
        return bookingRepository.findByShowingId(showing.getId());
    }

    public List<Booking> getAll(){
        return bookingRepository.findAll();
    }

    public Optional<Booking> getBookingByCustomerAndShowing(Long customerId, Long showingId){
        return bookingRepository.findBookingByCustomerIdAndShowingId(customerId,showingId);
    }

}
