package com.ticgrp10.WTFCINEMA.Services;

import com.ticgrp10.WTFCINEMA.Entities.Movie;
import com.ticgrp10.WTFCINEMA.Repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieServices {

    @Autowired
    private MovieRepository movieRepository;

    public Movie addMovie(Movie movie) {
        if (movieRepository.findById(movie.getId()).isPresent()) {
            throw new IllegalArgumentException("El snack ya existe");
        }
        if (movie.getTitle() == null || movie.getTitle().isEmpty()) {
            throw new IllegalArgumentException("El titulo no puede estar vacio");
        }
        if (movie.getSynopsis() == null || movie.getSynopsis().isEmpty()) {
            throw new IllegalArgumentException("La synopsis no puede estar vacia");
        }
        if (movie.getGenres().isEmpty()){
            throw new IllegalArgumentException("La genres no puede estar vacia");
        }
        if (movie.getAgeRating() < 0 || movie.getAgeRating() > 100) {
            throw new IllegalArgumentException("El genre no puede estar vacio");
        }
        if (movie.getLength() <= 0){
            throw new IllegalArgumentException("El length no puede estar vacio");
        }
        if (movie.getImg().isEmpty()){
            throw new IllegalArgumentException("La imagen no puede estar vacio");
        }

        return movieRepository.save(movie);
    }

    public List<Movie> getAll() {
        return movieRepository.findAll();
    }

    public Movie getMovieById(Long movieId) {
        Optional<Movie> optionalMovie = movieRepository.findById(movieId);
        return optionalMovie.orElse(null);
    }
}
