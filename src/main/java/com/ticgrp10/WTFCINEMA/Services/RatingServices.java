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

    public Rating addRating(Rating rating) {
        if (ratingRepository.existsById(rating.getId())) {
            Rating rating1 = ratingRepository.findById(rating.getId()).get();
            rating1.setRating(rating.getRating());
            ratingRepository.save(rating1);
        }
        return ratingRepository.save(rating);
    }
}
