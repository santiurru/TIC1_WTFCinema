package Repositories;

import Entities.Showing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShowingRepository extends JpaRepository<Showing, Long> {
}
