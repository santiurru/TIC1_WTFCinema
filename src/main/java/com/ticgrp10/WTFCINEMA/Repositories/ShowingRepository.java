package com.ticgrp10.WTFCINEMA.Repositories;

import com.ticgrp10.WTFCINEMA.Entities.Showing;
import com.ticgrp10.WTFCINEMA.Entities.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;


public interface ShowingRepository extends JpaRepository<Showing, Long> {
    List<Showing> findByRoomIdAndDate(Long roomId, LocalDateTime date);

    List<Showing> findShowingsByMovieIdAndRoomIdIn(Long movieId, List<Long> roomIds);

    List<Showing> findShowingsByMovieId(Long movieId);

    List<Showing> findShowingsByDateBetweenAndAndRoomId(LocalDateTime start, LocalDateTime end, long roomId);
//
    List<Long> findTheaterIdsByMovieId(Long movieId);

    List<Long> findRoomIdsByMovieId(Long movieId);


    @Query("SELECT DISTINCT s.roomId FROM Showing s WHERE s.date >= :date AND s.movieId = :movieId")
    List<Long> findRoomIdsByMovieIdAndDate(Long movieId,LocalDateTime date);

    @Query(" SELECT DISTINCT t FROM Theatre t JOIN Room r ON t.theatreId = r.theatreId JOIN Showing s ON r.id = s.roomId WHERE s.movieId = :movieId AND s.date >= :date")
    List<Theatre> findTheatres(Long movieId, LocalDateTime date);

    @Query("SELECT DISTINCT s.date FROM Theatre t JOIN Room r ON t.theatreId = r.theatreId JOIN Showing s ON r.id = s.roomId WHERE s.date >= :currentDate AND s.movieId = :movieId AND t.theatreId = :theatreId")
    List<LocalDateTime> findDateByMovieAndTheatre(Long movieId,Long theatreId, LocalDateTime currentDate);

    @Query("SELECT s.movieId FROM Showing s WHERE s.date >= :date")
    List<Long> findMovieIdByDate(@Param("date") LocalDateTime date);

    List<Showing> findShowingsByMovieIdAndDateAndRoomIdIn(Long movieId, LocalDateTime date,List<Long> roomIds);

    @Query("SELECT DISTINCT s FROM Theatre t JOIN Room r ON t.theatreId = r.theatreId JOIN Showing s ON r.id = s.roomId WHERE s.movieId = :movieId AND t.theatreId = :theatreId AND s.date = :date")
    List<Showing> findShowingByMovieAndTheatreAndDate(Long movieId,Long theatreId,LocalDateTime date);

}
