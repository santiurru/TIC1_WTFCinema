//package com.ticgrp10.WTFCINEMA.Services;
//
//import com.ticgrp10.WTFCINEMA.Entities.SeatAvailability;
//import com.ticgrp10.WTFCINEMA.Repositories.SeatAvailabilityRepository;
//import com.ticgrp10.WTFCINEMA.Repositories.ShowingRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Service
//public class SeatAvailabilityServices {
//
//    @Autowired
//    private SeatAvailabilityRepository seatsRepository;
//
//    @Autowired
//    private ShowingRepository showingRepository;
//
//    // Function to return all seats and their states for a given screening
//    public List<SeatAvailability> getAllSeatsByidScreening(Long showingId) {
//        List<SeatAvailability> bookedSeats = seatsRepository.getAllBookedSeatsByShowingId(showingId);
//        return bookedSeats;
//    }
//
//    public void createAndBookSeat(SeatAvailability seat) {
//        seatsRepository.save(seat);
//    }
//
//    // You can also implement the method to find by screening ID if needed
//    public SeatAvailability findBySeatRowAndSeatColAndScreeningId(int seatRow, int seatCol, Long screeningId) {
//        return seatsRepository.findBySeatRowAndSeatColAndScreeningId(seatRow, seatCol, screeningId);
//    }
//
//    public void deleteSeatsByBookingScreening(Long bookingScreeningId) {
//        seatsRepository.deleteByBookingScreeningId(bookingScreeningId);
//    }
//
//    public List<SeatAvailability> getSeatsByScreeningId(Long screeningId) {
//        return seatsRepository.findByScreeningId(screeningId);
//    }
//
//
//
//}
