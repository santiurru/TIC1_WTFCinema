package com.ticgrp10.WTFCINEMA.Repositories;

import com.ticgrp10.WTFCINEMA.Entities.Booking;
import com.ticgrp10.WTFCINEMA.Entities.WebUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByCustomerId(Long userId);

    List<Booking> findByShowingId(Long showingId);
}
