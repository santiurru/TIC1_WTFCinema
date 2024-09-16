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
public class Showing {
    private long id;
    private Room room;
    private Movie movie;
    private List<Showing> showings = new ArrayList<>();
}
