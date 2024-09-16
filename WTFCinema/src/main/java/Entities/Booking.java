package Entities;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Booking {
    private User user;
    private Seat seat;
    private Showing showing;

}
