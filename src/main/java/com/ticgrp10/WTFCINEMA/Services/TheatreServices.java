package com.ticgrp10.WTFCINEMA.Services;

import com.ticgrp10.WTFCINEMA.Entities.Movie;
import com.ticgrp10.WTFCINEMA.Entities.Theatre;
import com.ticgrp10.WTFCINEMA.Exceptions.ExistingEntity;
import com.ticgrp10.WTFCINEMA.Repositories.TheatreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TheatreServices {
    @Autowired
    private TheatreRepository theatreRepository;

    public void addTheatre(Theatre theatre) throws ExistingEntity {
        Optional<Theatre> theatreByNH = theatreRepository.findTheatreByNeighborhood(theatre.getNeighborhood());
        if (!theatreByNH.isPresent()){
            theatreRepository.save(theatre);
        }
    }

    public List<Theatre> getAll(){return theatreRepository.findAll();}

    public Theatre getTheatreById(Long theatreId){
        Optional<Theatre> optionalTheatre = theatreRepository.findById(theatreId);
        return optionalTheatre.orElse(null);
    }
}
