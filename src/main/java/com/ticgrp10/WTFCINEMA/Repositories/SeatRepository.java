package com.ticgrp10.WTFCINEMA.Repositories;
import com.ticgrp10.WTFCINEMA.Entities.Seat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {
    @Query("SELECT s FROM Booking b JOIN Seat s On b.id = s.bookingId WHERE b.showingId = :showingId")
    List<Seat> getSeatsByShowingId(Long showingId);

//    @Query("SELECT s.seatRow,s.seatColumn FROM Booking b JOIN Seat s On b.id = s.bookingId WHERE b.showingId = :showingId")
//    List<String> getSeatsByShowingIdAux(Long showingId);



}