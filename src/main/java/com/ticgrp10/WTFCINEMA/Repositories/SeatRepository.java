package com.ticgrp10.WTFCINEMA.Repositories;
import com.ticgrp10.WTFCINEMA.Entities.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    @Query("SELECT s FROM Booking b JOIN Seat s On b.id = s.bookingId WHERE b.showingId = :showingId")
    List<Seat> getSeatsByShowingId(Long showingId);

    List<Seat> getSeatsByBookingId(Long bookingId);

    @Query("SELECT s FROM Booking b JOIN Seat s On b.id = s.bookingId WHERE b.customerId =:userId and s.paid =:paid")
    List<Seat> getSeatsByUserIdAndPaid(long userId, Boolean paid);

    @Query("SELECT s.seatId FROM Booking b JOIN Seat s On b.id = s.bookingId WHERE b.customerId =:userId and s.paid =:paid")
    List<Long> findIdsByUserIdAndPaid(long userId, Boolean paid);

    Optional<Seat> findBySeatColumnAndSeatRowAndBookingId(int seatColumn, int seatRow, long bookingId);



}