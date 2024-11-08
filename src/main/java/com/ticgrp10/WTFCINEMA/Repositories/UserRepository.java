package com.ticgrp10.WTFCINEMA.Repositories;

import com.ticgrp10.WTFCINEMA.Entities.WebUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<WebUser, Long> {

    public Optional<WebUser> findByEmail(String email);

    public boolean existsByEmail(String email);
}
