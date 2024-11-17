package com.ticgrp10.WTFCINEMA.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
public class Seat {

    //@ManyToOne
//    @JoinColumn(name = "booking_id")
//    private Long bookingId;

    @Id
    @GeneratedValue
    private Long seatId;
    private int seatRow;
    private int seatColumn;

    private Long bookingId;



}
