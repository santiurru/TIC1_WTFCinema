package com.ticgrp10.WTFCINEMA.Services;

import com.ticgrp10.WTFCINEMA.Entities.Movie;
import com.ticgrp10.WTFCINEMA.Entities.Rating;
import com.ticgrp10.WTFCINEMA.Entities.WebUser;
import com.ticgrp10.WTFCINEMA.Repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RatingServices {

    @Autowired
    RatingRepository ratingRepository;

    public Float calculateAverageRating(long movieId) {
        List<Rating> ratings = ratingRepository.findAllByMovieId(movieId);
        return (float) ratings.stream()
                .mapToDouble(Rating::getRating) // Asumiendo que `Rating` tiene un campo `rating`
                .average()
                .orElse(-1); // Devuelve -1 si no tiene ratings todavia
    }

    public List<Rating> orderByRating(){
        List<Rating> allRatings = ratingRepository.findAll();
        for (Rating rating: allRatings){

        }
        return null;
    }


public List<Movie> order(List<AbstractMap.SimpleEntry<Movie, Float>> list) {
    int[] count = new int[100];
    List<Movie>[] aux = new List[100];
    List<Movie> sortedList = new ArrayList<>();

    // Inicializar cada posición de aux con una lista vacía
    for (int i = 0; i < aux.length; i++) {
        aux[i] = new ArrayList<>();
    }

    // Paso 1: Contamos la frecuencia de cada rating y llenamos los buckets
    for (AbstractMap.SimpleEntry<Movie, Float> movie : list) {
        int index = (int) (movie.getValue() * 10);
        if (index == -10){
            count[0]++;
            aux[0].add(movie.getKey());
        }else{
            count[index]++;
            aux[index].add(movie.getKey());
        }
    }

    // Paso 2: Reconstruir la lista ordenada
    for (int i = 99; i >= 0; i--) { // Cambiar a orden inverso si quieres de mayor a menor
        while (count[i] > 0) {
            sortedList.add(aux[i].getFirst());
            aux[i].removeFirst();
            count[i]--;
        }
    }

    return sortedList;
}



    public Rating addOrUpdateRating(Rating rating) {
        Optional<Rating> existingRating = ratingRepository.findByMovieIdAndCustomerId(rating.getMovieId(), rating.getCustomerId());

        if (existingRating.isPresent()) {
            // Si ya existe una calificación, la actualiza
            Rating existing = existingRating.get();
            existing.setRating(rating.getRating());
            return ratingRepository.save(existing);
        } else {
            // Si no existe una calificación, la guarda
            return ratingRepository.save(rating);
        }
    }
}
