//package com.ticgrp10.WTFCINEMA.Services;
//
//import com.ticgrp10.WTFCINEMA.Entities.Admin;
//import com.ticgrp10.WTFCINEMA.Entities.CustomUserDetails;
//import com.ticgrp10.WTFCINEMA.Entities.WebUser;
//import com.ticgrp10.WTFCINEMA.Repositories.AdminRepository;
//import com.ticgrp10.WTFCINEMA.Repositories.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//import java.util.Optional;
//
//@Service
//public class CustomUserDetailsService implements UserDetailsService {
//
//    @Autowired
//    private UserRepository webUserRepository;
//
//    @Autowired
//    private AdminRepository adminRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String email) throws org.springframework.security.core.userdetails.UsernameNotFoundException {
//        Optional<WebUser> user = webUserRepository.findByEmail(email);
//        if (user.isPresent()) {
//            return new CustomUserDetails(user.orElse(null));
//        }
//
//        Optional<Admin> admin = adminRepository.findByEmail(email);
//        if (admin.isPresent()) {
//            return new CustomUserDetails(admin.orElse(null));
//        } else {
//            throw new UsernameNotFoundException("User with email: " + email + " not found");
//        }
//    }
//}
//
