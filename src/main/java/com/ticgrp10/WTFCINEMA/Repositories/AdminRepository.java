package com.ticgrp10.WTFCINEMA.Repositories;

import com.ticgrp10.WTFCINEMA.Entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {

    public Optional<Admin> findByEmail(String email);

    public boolean existsByEmail(String email);


}
