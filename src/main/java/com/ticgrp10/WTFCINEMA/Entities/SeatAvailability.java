//package com.ticgrp10.WTFCINEMA.Entities;
//
//import com.fasterxml.jackson.annotation.JsonIgnore;
//import jakarta.persistence.*;
//import lombok.AllArgsConstructor;
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@Entity
//public class SeatAvailability {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long idSeat;
//
//    private int seatRow;
//    private int seatCol;
//
//    @JsonIgnore
//    @ManyToOne
//    @JoinColumn(name = "id")
//    private Booking booking;
//}
//
