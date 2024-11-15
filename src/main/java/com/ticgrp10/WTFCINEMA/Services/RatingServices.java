package com.ticgrp10.WTFCINEMA.Services;

import com.ticgrp10.WTFCINEMA.Entities.Rating;
import com.ticgrp10.WTFCINEMA.Repositories.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RatingServices {

    @Autowired
    RatingRepository ratingRepository;

    public Rating addOrUpdateRating(Rating rating) {
        // Verificar si ya existe una calificación de este usuario para la película
        Optional<Rating> existingRating = ratingRepository.findByMovieIdAndCustomerId(rating.getMovieId(), rating.getCustomerId());

        if (existingRating.isPresent()) {
            // Si ya existe una calificación, actualízala
            Rating existing = existingRating.get();
            existing.setRating(rating.getRating());
            return ratingRepository.save(existing);
        } else {
            // Si no existe una calificación, guárdala
            return ratingRepository.save(rating);
        }
    }
}
