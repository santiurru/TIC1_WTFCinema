package com.ticgrp10.WTFCINEMA.Services;

import com.ticgrp10.WTFCINEMA.Entities.Room;
import com.ticgrp10.WTFCINEMA.Entities.Theatre;
import com.ticgrp10.WTFCINEMA.Repositories.RoomRepository;
import com.ticgrp10.WTFCINEMA.Repositories.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        return roomRepository.save(room);
    }


}
