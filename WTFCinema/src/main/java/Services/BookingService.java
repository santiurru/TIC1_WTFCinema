package Services;

import Entities.Booking;
import Entities.Seat;
import Entities.Showing;
import Entities.WebUser;
import Repositories.BookingRepository;
import Repositories.SeatRepository;
import Repositories.ShowingRepository;
import Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository webUserRepository;

    @Autowired
    private SeatRepository seatRepository;

    @Autowired
    private ShowingRepository showingRepository;

    public Booking addBooking(Booking booking) {
        Optional<WebUser> userOptional = webUserRepository.findById(booking.getCustomer().getId());
        if (userOptional.isEmpty()) {
            throw new IllegalArgumentException("Customer not found");
        }

        Optional<Seat> seatOpt = seatRepository.findByRoomAndRowAndNumber(booking.getSeat().getRoom(), booking.getSeat().getRow(), booking.getSeat().getNumber());
        if (seatOpt.isEmpty()) {
            throw new IllegalArgumentException("Seat not found");
        }

        Optional<Showing> showingOpt = showingRepository.findById(booking.getShowing().getId());
        if (showingOpt.isEmpty()) {
            throw new IllegalArgumentException("Showing not found");
        }

        return bookingRepository.save(booking);
    }

    public List<Booking> getBookingsForUser(Long userId) {
        return bookingRepository.findByCustomerId(userId);
    }

    public List<Booking> getBookingsForShowing(Long showingId) {
        return bookingRepository.findByShowingId(showingId);
    }
}
