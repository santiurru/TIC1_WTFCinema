package com.ticgrp10.WTFCINEMA.Repositories;

import com.ticgrp10.WTFCINEMA.Entities.Showing;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;


public interface ShowingRepository extends JpaRepository<Showing, Long> {
    List<Showing> findByRoomIdAndDate(Long roomId, LocalDateTime date);
}
