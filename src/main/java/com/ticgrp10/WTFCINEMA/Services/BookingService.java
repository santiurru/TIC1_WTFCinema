package com.ticgrp10.WTFCINEMA.Services;

import com.ticgrp10.WTFCINEMA.Entities.Booking;
import com.ticgrp10.WTFCINEMA.Entities.Showing;
import com.ticgrp10.WTFCINEMA.Entities.WebUser;
import com.ticgrp10.WTFCINEMA.Repositories.BookingRepository;
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
    private ShowingRepository showingRepository;

    @Autowired
    private ShowingServices showingServices;

    public Booking addBooking(Booking booking) {
        Optional<WebUser> userOptional = webUserRepository.findById(booking.getCustomerId());
        if (userOptional.isEmpty()) {throw new IllegalArgumentException("Customer not found");}

        int seat = booking.getSeatId();
        if (seat < 0 || seat > 150){throw new IllegalArgumentException("Asiento invalido");}
        Optional<Showing> showingOptional = showingRepository.findById(booking.getShowingId());
        if (!showingOptional.isPresent()) {throw new IllegalArgumentException("Showing not found");}
        Showing showing = showingOptional.get();
        if (!showingServices.notAvailableSeats(showing.getId()).contains(seat)) {
            return bookingRepository.save(booking);
        }
        else {throw new IllegalArgumentException("El asiento no está disponible.");}
    }

    // Cancelación de reserva
    public String cancelBooking(WebUser user, Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).get();
        WebUser webUser = webUserRepository.findById(booking.getCustomerId()).get();
        if (!webUser.equals(user)) {
            throw new IllegalStateException("No puedes cancelar una reserva que no te pertenece.");
        }
        bookingRepository.delete(booking);
        return "Reserva cancelada exitosamente.";
    }

    // Obtener reservas del usuario
    public List<Booking> getUserBookings(WebUser user) {
        return bookingRepository.findByCustomerId(user.getId());
    }

    public List<Booking> getShowingBookings(Showing showing){
        return bookingRepository.findByShowingId(showing.getId());
    }

    public List<Booking> getAll(){
        return bookingRepository.findAll();
    }
}
