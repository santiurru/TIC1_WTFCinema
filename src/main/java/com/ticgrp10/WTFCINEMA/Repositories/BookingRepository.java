package com.ticgrp10.WTFCINEMA.Repositories;

import com.ticgrp10.WTFCINEMA.Entities.Booking;
import com.ticgrp10.WTFCINEMA.Entities.WebUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    @Query("SELECT b from Booking b join Showing s On b.showingId = s.id where s.date >= :date and b.customerId = :userId")
    List<Booking> findByCustomerIdAndDate(Long userId, LocalDateTime date);

    List<Booking> findByCustomerId(Long userId);

    List<Booking> findByShowingId(Long showingId);

    Optional<Booking> findBookingByCustomerIdAndShowingId(Long customerId,Long showingId);
}
