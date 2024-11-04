package com.ticgrp10.WTFCINEMA.Entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Theatre {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long theatre_id;

    @NotNull
    private String neighborhood;

    public Theatre(String neighborhood) {
        this.neighborhood = neighborhood;
    }


//    private List<Movie> billboard = new ArrayList<Movie>();
//
//    private List<Room> rooms = new ArrayList<Room>();

    @Override
    public String toString() {
        return "Theatre{" +
                "theatre_id=" + theatre_id +
                ", neighborhood='" + neighborhood + '\'' +
                '}';
    }
}
