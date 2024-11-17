package com.ticgrp10.WTFCINEMA.Entities;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private long customerId;

    @NotNull
    private long showingId;

//    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
////    @JoinColumn(name = "booking_id")
//    private List<Seat> seatList;


}
