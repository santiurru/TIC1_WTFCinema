package com.ticgrp10.WTFCINEMA.Repositories;

import com.ticgrp10.WTFCINEMA.Entities.Showing;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


public interface ShowingRepository extends JpaRepository<Showing, Long> {
    List<Showing> findByRoomIdAndDate(Long roomId, LocalDateTime date);

    List<Showing> findShowingsByMovieIdAndRoomIdIn(Long movieId, List<Long> roomIds);
//
    List<Long> findTheaterIdsByMovieId(Long movieId);

    List<Long> findRoomIdsByMovieId(Long movieId);
    @Query("SELECT s.roomId FROM Showing s WHERE s.date >= :date AND s.movieId = :movieId")
    List<Long> findRoomIdsByMovieIdAndDate(Long movieId,LocalDateTime date);


    @Query("SELECT s.movieId FROM Showing s WHERE s.date >= :date")
    List<Long> findMovieIdByDate(@Param("date") LocalDateTime date);

    List<Showing> findShowingsByMovieIdAndDateAndRoomIdIn(Long movieId, LocalDateTime date,List<Long> roomIds);
}
