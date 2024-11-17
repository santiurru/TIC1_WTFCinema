package com.ticgrp10.WTFCINEMA.Entities;

import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class PurchaseSnack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private long customerId;

    @NotNull
    private long snackId;

    @Nullable
    private  long showingId;

    @NotNull
    private int quantity = 1;

    private LocalDateTime bookingDate;

    private boolean paid;

    @Transient
    private String name;

    @Transient
    private float price;
}
