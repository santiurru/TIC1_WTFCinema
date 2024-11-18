package com.ticgrp10.WTFCINEMA.Configs;

import com.ticgrp10.WTFCINEMA.Entities.Theatre;
import com.ticgrp10.WTFCINEMA.Repositories.TheatreRepository;
import com.ticgrp10.WTFCINEMA.Services.TheatreServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Configuration
public class TheatreConfig {
    @Autowired
    private TheatreServices theatreServices;

    @Bean
    @Order(1)
    CommandLineRunner commandLineRunnerTheatre (TheatreRepository theatreRepository) {
        return args -> {
            Theatre puntaCarretas = new Theatre("Punta Carretas");
            theatreServices.addTheatre(puntaCarretas);
            Theatre ciudadVieja = new Theatre("Ciudad Vieja");
            theatreServices.addTheatre(ciudadVieja);
            Theatre pocitos = new Theatre("Pocitos");
            theatreServices.addTheatre(pocitos);
            Theatre carrasco = new Theatre("Carrasco");
            theatreServices.addTheatre(carrasco);
            Theatre tresCruces = new Theatre("Tres Cruces");
            theatreServices.addTheatre(tresCruces);
            Theatre centro = new Theatre("Centro");
            theatreServices.addTheatre(centro);
            Theatre malvin = new Theatre("Malvín");
            theatreServices.addTheatre(malvin);
            Theatre buceo = new Theatre("Buceo");
            theatreServices.addTheatre(buceo);
        };
    }
}
