package com.ticgrp10.WTFCINEMA.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


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
    private int ageRating;

    @NotNull
    private String genres;

    @NotNull
    private long length;

    @NotNull
    @Column(length = 1000)
    private String img;

    @NotNull
    @Size(max = 5000)
    @Column(nullable = false, length = 5000)
    private String synopsis;
}
