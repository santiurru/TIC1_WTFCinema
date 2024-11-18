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


    @Bean
    @Order(2)
    CommandLineRunner commandLineRunnerRoom (RoomService roomService){
        return args -> {
            // punta carretas, 8 salas
            int lenPC = roomService.roomCount(1);
            if(lenPC == 0){
                for (int i = 0; i <8; i++ ){
                    Room temp = new Room(i,1);
                    roomService.addRoom(1,temp);
                }
            }
            int lenCV = roomService.roomCount(2);
            if(lenCV == 0){
                for (int i = 0; i <5; i++ ){
                    Room temp = new Room(i,2);
                    roomService.addRoom(2,temp);
                }
            }
            int lenP = roomService.roomCount(3);
            if(lenP == 0){
                for (int i = 0; i <7; i++ ){
                    Room temp = new Room(i,3);
                    roomService.addRoom(3,temp);
                }
            }
            int lenCa = roomService.roomCount(4);
            if(lenCa == 0){
                for (int i = 0; i <4; i++ ){
                    Room temp = new Room(i,4);
                    roomService.addRoom(4,temp);
                }
            }
            int lenTC = roomService.roomCount(5);
            if(lenTC == 0){
                for (int i = 0; i <6; i++ ){
                    Room temp = new Room(i,5);
                    roomService.addRoom(5,temp);
                }
            }
            int lenCe = roomService.roomCount(6);
            if(lenCe == 0){
                for (int i = 0; i <10; i++ ){
                    Room temp = new Room(i,6);
                    roomService.addRoom(6,temp);
                }
            }
            int lenMa = roomService.roomCount(7);
            if(lenMa == 0){
                for (int i = 0; i <3; i++ ){
                    Room temp = new Room(i,7);
                    roomService.addRoom(7,temp);
                }
            }
            int lenBu = roomService.roomCount(8);
            if(lenBu == 0){
                for (int i = 0; i <6; i++ ){
                    Room temp = new Room(i,8);
                    roomService.addRoom(8,temp);
                }
            }
        };
    }
}
