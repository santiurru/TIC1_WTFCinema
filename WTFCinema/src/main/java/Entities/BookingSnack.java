package Entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BookingSnack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private Snack snack;

    @NotNull
    private Booking booking;

    @NotNull
    private int quantity = 1;
}
