package Repositories;

import Entities.Booking;
import Entities.Snack;
import Entities.WebUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    Optional<List<Booking>> getBookingByCustomer(WebUser user);

    List<Booking> findByCustomerId(Long userId);

    List<Booking> findByShowingId(Long showingId);
}
