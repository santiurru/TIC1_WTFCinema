package com.ticgrp10.WTFCINEMA.Repositories;

import com.ticgrp10.WTFCINEMA.Entities.Showing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShowingRepository extends JpaRepository<Showing, Long> {
}
