package Repositories;

import Entities.Movie;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MovieRepository extends JpaRepository<Movie, Long> {
    List<Movie> findByIsInCinemaTrue();
    public Optional<Movie> findByTitle(String title);
}
