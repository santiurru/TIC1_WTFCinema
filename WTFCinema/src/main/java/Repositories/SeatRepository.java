package Repositories;

import Entities.Room;
import Entities.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface SeatRepository extends JpaRepository<Seat, Long> {
    Optional<Seat> findByRoomAndRowAndNumber(Room room, int row, int number);
}
