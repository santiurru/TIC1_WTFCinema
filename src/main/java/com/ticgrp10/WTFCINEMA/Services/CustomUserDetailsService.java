package com.ticgrp10.WTFCINEMA.Services;

import com.ticgrp10.WTFCINEMA.Entities.Admin;
import com.ticgrp10.WTFCINEMA.Entities.WebUser;
import com.ticgrp10.WTFCINEMA.Repositories.AdminRepository;
import com.ticgrp10.WTFCINEMA.Repositories.UserRepository;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final AdminRepository adminRepository;

    public CustomUserDetailsService(UserRepository userRepository, AdminRepository adminRepository) {
        this.userRepository = userRepository;
        this.adminRepository = adminRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (userRepository.existsByEmail(username)) {
            Optional<WebUser> user = userRepository.findByEmail(username);
            return new org.springframework.security.core.userdetails.User(
                    user.get().getEmail(), user.get().getPassword(),
                    List.of(new SimpleGrantedAuthority(user.get().getRole()))
            );
        } else if (adminRepository.existsByEmail(username)) {
            Optional<Admin> admin = adminRepository.findByEmail(username);
            return new org.springframework.security.core.userdetails.User(
                    admin.get().getEmail(), admin.get().getPassword(),
                    List.of(new SimpleGrantedAuthority(admin.get().getRole()))
            );
        }
        throw new UsernameNotFoundException("User not found");
    }
}
