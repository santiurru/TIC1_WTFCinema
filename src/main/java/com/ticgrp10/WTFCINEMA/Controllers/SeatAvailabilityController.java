package com.ticgrp10.WTFCINEMA.Controllers;


import com.ticgrp10.WTFCINEMA.Entities.SeatAvailability;
import com.ticgrp10.WTFCINEMA.Services.SeatAvailabilityServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/seats")
public class SeatAvailabilityController {

    @Autowired
    private SeatAvailabilityServices seatAvailabilityServices;

    /**
     * Retrieves the booked seats for a specific screening by its ID.
     *
     * @param idScreening the ID of the screening for which booked seats are requested
     * @return a ResponseEntity containing the list of booked seats and a 200 OK status
     */
    @GetMapping("/booked-seats/{idScreening}")
    public ResponseEntity<List<SeatAvailability>> getBookedSeats(@PathVariable Long idScreening) {

        List<SeatAvailability> seats = seatAvailabilityServices.getSeatsByScreeningId(idScreening);
        return ResponseEntity.ok(seats);
    }
}
