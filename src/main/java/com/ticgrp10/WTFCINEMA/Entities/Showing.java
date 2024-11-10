package com.ticgrp10.WTFCINEMA.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Showing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private long roomId;

    @NotNull
    private long movieId;

    @NotNull
    private LocalDateTime date;

    @Transient
    private String movieTitle;

    @Transient
    private String movieSynopsis;

    @Transient
    private String theatreName;

    @Transient
    private int roomNumber;

//    @NotNull
//    private List<List<Boolean>> seatAvailability;
}
