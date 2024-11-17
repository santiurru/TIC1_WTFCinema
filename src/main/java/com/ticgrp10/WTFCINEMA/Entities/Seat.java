package com.ticgrp10.WTFCINEMA.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Entity
@Table
@Getter
@Setter
public class Seat {
    @Id
    @GeneratedValue
    private Long seatId;

    private int seatRow;
    private int seatColumn;

    private Long bookingId;

    private LocalDate bookingDate;

    private boolean paid;

}
