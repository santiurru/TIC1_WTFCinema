package com.ticgrp10.WTFCINEMA.Repositories;

import com.ticgrp10.WTFCINEMA.Entities.Snack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SnackRepository extends JpaRepository<Snack, Long> {
    Optional<Snack> findByName(String name);

    Optional<Snack> findById(long id);

}
