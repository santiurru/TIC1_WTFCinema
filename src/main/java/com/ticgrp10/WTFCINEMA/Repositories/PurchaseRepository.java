package com.ticgrp10.WTFCINEMA.Repositories;

import com.ticgrp10.WTFCINEMA.Entities.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {

}
