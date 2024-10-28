package com.ticgrp10.WTFCINEMA.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.sql.Date;
import java.time.DateTimeException;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "movie")
public class Movie {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private String title;

    @NotNull
    private int ageRating;      //Boolean o int o String

    @NotNull
    private String genres;      //hacerle tostring a una lista de generos

    @NotNull
    private long length;

    @NotNull
    private String img;

    @NotNull
    private String synopsis;

    private boolean isInCinema;
}
