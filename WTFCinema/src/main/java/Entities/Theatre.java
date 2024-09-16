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
public class Theatre {

    private long theatre_id;
    private String location;
    private String name;
    private List<Movie> billboard = new ArrayList<Movie>();
    private List<Room> rooms = new ArrayList<Room>();

    //constuctor

    public Theatre(String location, String name) {
        this.location = location;
        this.name = name;
    }
}
