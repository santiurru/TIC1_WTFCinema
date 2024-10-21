package Services;

import Entities.Room;
import Entities.Theatre;
import Repositories.RoomRepository;
import Repositories.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private TheatreRepository theatreRepository;

    public Room addRoom(Theatre theatre, Room room) {
        Optional<Theatre> cinemaOptional = theatreRepository.findById(theatre.getTheatre_id());
        if (!cinemaOptional.isPresent()) {
            throw new IllegalArgumentException("El cine no existe.");
        }

        return roomRepository.save(room);
    }


}
