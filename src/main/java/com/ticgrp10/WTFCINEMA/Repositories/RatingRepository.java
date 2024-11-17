package com.ticgrp10.WTFCINEMA.Repositories;

import com.ticgrp10.WTFCINEMA.Entities.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findAllByMovieId(long movieId);

    Optional<Rating> findByMovieIdAndCustomerId(long movieId, long customerId);

    List<Rating> findByCustomerId(long customerId);

    public List<Rating> findByMovieId(long movieId);

}

