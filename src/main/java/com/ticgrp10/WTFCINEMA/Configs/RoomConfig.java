package com.ticgrp10.WTFCINEMA.Configs;

import com.ticgrp10.WTFCINEMA.Entities.Room;
import com.ticgrp10.WTFCINEMA.Services.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoomConfig {

    @Autowired
    private RoomService roomService;

    @Bean
    CommandLineRunner commandLineRunnerRoom (RoomService roomService){
        return args -> {
            // punta carretas, 8 salas
            //System.out.println("--------agregando rooms-------");
            int lenPC = roomService.roomCount(1L);
//            System.out.println("roomcount para theatre 1: ");
//            System.out.println(lenPC);
            if(lenPC == 0){
                for (int i = 0; i <8; i++ ){
                    Room temp = new Room(1L);
                    //System.out.println("adding room");
                    roomService.addRoom(1L,temp);
                }
            }
            int lenCV = roomService.roomCount(2L);
            if(lenPC == 0){
                for (int i = 0; i <5; i++ ){
                    Room temp = new Room(2L);
                    roomService.addRoom(2L,temp);
                }
            }
            int lenP = roomService.roomCount(3L);
            if(lenPC == 0){
                for (int i = 0; i <7; i++ ){
                    Room temp = new Room(3L);
                    roomService.addRoom(3L,temp);
                }
            }
            int lenCa = roomService.roomCount(4L);
            if(lenPC == 0){
                for (int i = 0; i <4; i++ ){
                    Room temp = new Room(4L);
                    roomService.addRoom(4L,temp);
                }
            }
            int lenCV = roomService.roomCount(2L);
            if(lenPC == 0){
                for (int i = 0; i <5; i++ ){
                    Room temp = new Room(2L);
                    roomService.addRoom(2L,temp);
                }
            }
        };
    }
}
