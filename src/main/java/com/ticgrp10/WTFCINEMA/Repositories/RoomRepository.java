package com.ticgrp10.WTFCINEMA.Repositories;
import com.ticgrp10.WTFCINEMA.Entities.Room;
import com.ticgrp10.WTFCINEMA.Entities.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {
    Optional<Room> findByNumberAndTheatreId(int number, long theatreId);
    List<Room> findAllByTheatreId(Long theatreId);

    List<Long> findIdsByTheatreId(Long theatreId);

    @Query("SELECT DISTINCT s.theatreId FROM Room s WHERE s.id in :roomIds")
    List<Long> findTheatreIdsByIdIn(List<Long> roomIds);



}
