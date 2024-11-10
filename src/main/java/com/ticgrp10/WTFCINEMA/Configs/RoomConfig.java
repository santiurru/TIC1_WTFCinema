package com.ticgrp10.WTFCINEMA.Configs;

import com.ticgrp10.WTFCINEMA.Entities.Room;
import com.ticgrp10.WTFCINEMA.Services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class RoomConfig {

    @Autowired
    private RoomService roomService;

    @Bean
    @Order(2)
    CommandLineRunner commandLineRunnerRoom (RoomService roomService){
        return args -> {
            // punta carretas, 8 salas
            //System.out.println("--------agregando rooms-------");
            int lenPC = roomService.roomCount(9);
//            System.out.println("roomcount para theatre 1: ");
//            System.out.println(lenPC);
            if(lenPC == 0){
                for (int i = 0; i <8; i++ ){
                    Room temp = new Room(i,9);
                    //System.out.println("adding room");
                    roomService.addRoom(9,temp);
                }
            }
            int lenCV = roomService.roomCount(10);
            if(lenCV == 0){
                for (int i = 0; i <5; i++ ){
                    Room temp = new Room(i,10);
                    roomService.addRoom(10,temp);
                }
            }
            int lenP = roomService.roomCount(11);
            if(lenP == 0){
                for (int i = 0; i <7; i++ ){
                    Room temp = new Room(i,11);
                    roomService.addRoom(11,temp);
                }
            }
            int lenCa = roomService.roomCount(12);
            if(lenCa == 0){
                for (int i = 0; i <4; i++ ){
                    Room temp = new Room(i,12);
                    roomService.addRoom(12,temp);
                }
            }
            int lenTC = roomService.roomCount(13);
            if(lenTC == 0){
                for (int i = 0; i <6; i++ ){
                    Room temp = new Room(i,13);
                    roomService.addRoom(13,temp);
                }
            }
            int lenCe = roomService.roomCount(14);
            if(lenCe == 0){
                for (int i = 0; i <10; i++ ){
                    Room temp = new Room(i,14);
                    roomService.addRoom(14,temp);
                }
            }
            int lenMa = roomService.roomCount(15);
            if(lenMa == 0){
                for (int i = 0; i <3; i++ ){
                    Room temp = new Room(i,15);
                    roomService.addRoom(15,temp);
                }
            }
            int lenBu = roomService.roomCount(16);
            if(lenBu == 0){
                for (int i = 0; i <6; i++ ){
                    Room temp = new Room(i,16);
                    roomService.addRoom(16,temp);
                }
            }
        };
    }
}
