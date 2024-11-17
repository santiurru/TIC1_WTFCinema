package com.ticgrp10.WTFCINEMA.Services;

import com.ticgrp10.WTFCINEMA.Entities.Movie;
import com.ticgrp10.WTFCINEMA.Entities.PurchaseSnack;
import com.ticgrp10.WTFCINEMA.Entities.Seat;
import com.ticgrp10.WTFCINEMA.Entities.Showing;
import com.ticgrp10.WTFCINEMA.Repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MovieServices {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private ShowingRepository showingRepository;

    @Autowired
    private ShowingServices showingServices;

    @Autowired
    private PurchaseSnackRepository purchaseSnackRepository;

    @Autowired
    private SnackRepository snackRepository;

    @Autowired
    private SeatRepository seatRepository;

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

    public float getEarnings(Long movieId){
////        float sum = 0;
////        List<Showing> showings = showingRepository.findShowingsByMovieId(movieId);
////        for (int i=0; i < showings.size() ; i++){
////            List<PurchaseSnack> snackPurchases = purchaseSnackRepository.findByShowingId(showings.get(i).getId());
////            for (int j = 0; j < snackPurchases.size(); j++){
////                float price = snackRepository.findById(snackPurchases.get(j).getSnackId()).get().getPrice();
////                int quantity = snackPurchases.get(j).getQuantity();
////                sum += price*quantity;
////            }
////            int count = showingServices.notAvailableSeats(showings.get(i).getId()).size();
////            sum += count * showings.get(i).getTicketPrice();
////        }
////        return sum;
        float total = 0;
        int seatCount =0;
        List<Showing> showings = showingRepository.findShowingsByMovieId(movieId);
        for (Showing showing : showings) {
            List<Seat> seatList = seatRepository.getSeatsByShowingId(showing.getId());
        }
        
        return total;
    }


    public float getRating(long movieId){

        return 0;
    }
}
