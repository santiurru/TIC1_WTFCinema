package Entities;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.sql.Date;
import java.time.DateTimeException;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Movie")
public class Movie {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String title;

    @NotNull
    private int ageRating;      //Boolean o int o String

    @NotNull
    private String genres;      //hacerle tostring a una lista de generos

    @NotNull
    private long length;

    @NotNull
    private String poster;

    @NotNull
    private String synopsis;

    @NotNull
    private Date releaseDate;


    private boolean isInCinema;
}
