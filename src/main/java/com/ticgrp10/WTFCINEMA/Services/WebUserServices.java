package com.ticgrp10.WTFCINEMA.Services;

import com.ticgrp10.WTFCINEMA.Entities.WebUser;
import com.ticgrp10.WTFCINEMA.Exceptions.ExistingEntity;
import com.ticgrp10.WTFCINEMA.Exceptions.InvalidDataException;
import com.ticgrp10.WTFCINEMA.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class WebUserServices implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    public WebUser addUser(WebUser user) {
        if (user.getName() == null || user.getName().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede estar vacío");
        }
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Ya existe un usuario con ese correo electrónico");
        }
        if (!isValidEmail(user.getEmail())) {
            throw new IllegalArgumentException("El formato del correo electrónico no es válido");
        }

        return userRepository.save(user);
    }

    public Optional<WebUser> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public List<WebUser> allUsers(){
        return userRepository.findAll();
    }

    public Optional<WebUser> findById(Long id) {
        return userRepository.findById(id);
    }

    public void addCreditCard(WebUser user, long cardNumber, String ownerName, String expirationDate, int cvv){
        user.setCardNumber(cardNumber);
        user.setOwnerName(ownerName);
        user.setExpirationDate(expirationDate);
        user.setCvv(cvv);
        userRepository.save(user);
    }


    private boolean isValidEmail(String email) {
        String emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        return email.matches(emailRegex);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<WebUser> user = userRepository.findByEmail(email);

        if (user.isPresent()) {
            var springUser = User.withUsername(user.get().getEmail())
                    .password(user.get().getPassword())
                    .roles("USER")
                    .build();
            return springUser;
        }
        return null;
    }
}
