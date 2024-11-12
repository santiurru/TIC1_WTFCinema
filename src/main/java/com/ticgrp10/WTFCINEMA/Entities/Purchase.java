package com.ticgrp10.WTFCINEMA.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private long customerId;

    @OneToMany
    private List<Booking> bookings;

    @OneToMany
    private List<PurchaseSnack> snacks;

    @NotNull
    private Date purchaseDate;

    @NotNull
    private double totalPrice=0;


}
