package com.ticgrp10.WTFCINEMA.Repositories;
import com.ticgrp10.WTFCINEMA.Entities.Room;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByNumberAndTheatreId(int number, long theatreId);
    List<Room> findAllByTheatreId(Long TheatreId);
    List<Room> findByTheatreId(Long cinemaId);

}
