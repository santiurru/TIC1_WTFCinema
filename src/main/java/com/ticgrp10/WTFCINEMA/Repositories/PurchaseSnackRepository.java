package com.ticgrp10.WTFCINEMA.Repositories;

import com.ticgrp10.WTFCINEMA.Entities.PurchaseSnack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PurchaseSnackRepository extends JpaRepository<PurchaseSnack, Long> {
    List<PurchaseSnack> findByShowingId(Long showingId);

    List<PurchaseSnack> findByCustomerId(Long customerId);

    List<PurchaseSnack> findByCustomerIdAndPaid(Long CustomerId, Boolean Paid);

    @Query("SELECT ps.id FROM PurchaseSnack ps WHERE ps.customerId =:userId and ps.paid =:paid")
    List<Long> findIdsByCustomerIdAndPaid(Long userId, Boolean paid);

    List<PurchaseSnack> findByCustomerIdAndShowingId(long customerId, long showingId);
}
