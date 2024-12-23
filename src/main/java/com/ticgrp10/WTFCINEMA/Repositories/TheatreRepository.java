package com.ticgrp10.WTFCINEMA.Repositories;

import com.ticgrp10.WTFCINEMA.Entities.Theatre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TheatreRepository extends JpaRepository<Theatre, Long> {

    @Query("SELECT t FROM Theatre t WHERE t.neighborhood = ?1")
    Optional<Theatre> findTheatreByNeighborhood(String neighborhood);

    @Query("SELECT DISTINCT s FROM Theatre s WHERE s.theatreId in :theatreIds")
    List<Theatre> findByTheatreIdIn(List<Long> theatreIds);

}
