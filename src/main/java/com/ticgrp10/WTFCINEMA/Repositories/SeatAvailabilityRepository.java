package com.ticgrp10.WTFCINEMA.Repositories;


import com.ticgrp10.WTFCINEMA.Entities.SeatAvailability;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatAvailabilityRepository extends JpaRepository<SeatAvailability, Long> {
    // devuelve asientos OCUPADOS por idScreening (todos los asientos)
    @Query("SELECT s FROM SeatAvailability s WHERE s.booking.showingId = :idScreening")
    List<SeatAvailability> getAllBookedSeatsByShowingId(@Param("showingId") Long showingId);

    // Query to find all seats by screening ID
    @Query("SELECT s FROM SeatAvailability s WHERE s.booking.showingId = :showingId")
    List<SeatAvailability> findByScreeningId(@Param("showingId") Long showingId);

    //para borrar seats por id bs
    @Modifying
    @Transactional
    @Query("DELETE FROM SeatAvailability s WHERE s.booking.id = :bookingId")
    void deleteByBookingScreeningId(@Param("bookingId") Long bookingId);

    // Query to find a seat by row, column, and screening ID
    @Query("SELECT s FROM SeatAvailability s WHERE s.seatRow = :seatRow AND s.seatCol = :seatCol AND s.booking.showingId = :showingId")
    SeatAvailability findBySeatRowAndSeatColAndScreeningId(@Param("seatRow") int seatRow, @Param("seatCol") int seatCol, @Param("showingId") Long showingId);
}
