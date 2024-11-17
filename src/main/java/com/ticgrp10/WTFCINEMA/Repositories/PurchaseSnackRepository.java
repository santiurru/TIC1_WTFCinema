package com.ticgrp10.WTFCINEMA.Repositories;

import com.ticgrp10.WTFCINEMA.Entities.PurchaseSnack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseSnackRepository extends JpaRepository<PurchaseSnack, Long> {
    List<PurchaseSnack> findByShowingId(Long showingId);

    List<PurchaseSnack> findByCustomerId(Long customerId);

//    List<PurchaseSnack> getPurchaseSnackByCustomerIdAndPaid(Long CustomerId, Boolean Paid);

}
