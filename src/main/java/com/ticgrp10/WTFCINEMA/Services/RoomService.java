package com.ticgrp10.WTFCINEMA.Services;

import com.ticgrp10.WTFCINEMA.Entities.Movie;
import com.ticgrp10.WTFCINEMA.Entities.Room;
import com.ticgrp10.WTFCINEMA.Entities.Theatre;
import com.ticgrp10.WTFCINEMA.Repositories.RoomRepository;
import com.ticgrp10.WTFCINEMA.Repositories.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private TheatreRepository theatreRepository;

    public Room addRoom(long theatreId, Room room) {
        Optional<Theatre> theatreOptional = theatreRepository.findById(theatreId);
        if (theatreOptional.isEmpty()) {
            throw new IllegalArgumentException("El cine no existe.");
        }
        Optional<Room> roomOptional = roomRepository.findByNumberAndTheatreId(room.getNumber(), theatreId);

        return roomRepository.save(room);
    }
    public int roomCount(long theatreId){
        List<Room> lista = roomRepository.findAllByTheatreId(theatreId);
        return lista.size();
    }
    public List<Room> getAll() {
        return roomRepository.findAll();
    }

    public List<Room> getRoomsByTheatre(Long theatreId) {
        return roomRepository.findAllByTheatreId(theatreId);
    }

    public Room getRoomById(long roomId){
        Optional<Room> optionalRoom = roomRepository.findById(roomId);
        return optionalRoom.orElse(null);

    }
}
