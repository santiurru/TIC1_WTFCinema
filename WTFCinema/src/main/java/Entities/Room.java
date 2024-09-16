package Entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Room {
    private List<Seat> seats = new ArrayList<>();
    private Theatre theatre;
    private List<Showing> showings = new ArrayList<>();



}
