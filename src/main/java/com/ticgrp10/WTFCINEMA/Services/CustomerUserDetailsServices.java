//package com.ticgrp10.WTFCINEMA.Services;
//
//import com.ticgrp10.WTFCINEMA.Entities.Admin;
//import com.ticgrp10.WTFCINEMA.Entities.CustomUserDetails;
//import com.ticgrp10.WTFCINEMA.Entities.WebUser;
//import com.ticgrp10.WTFCINEMA.Exceptions.UsernameNotFoundException;
//import com.ticgrp10.WTFCINEMA.Repositories.AdminRepository;
//import com.ticgrp10.WTFCINEMA.Repositories.UserRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
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
//    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
//        WebUser user = webUserRepository.findByEmail(email);
//        if (user != null) {
//            return new CustomUserDetails(user);
//        }
//
//        Admin admin = adminRepository.findByEmail(email);
//        if (admin != null) {
//            return new CustomUserDetails(admin);
//        }
//
//        throw new UsernameNotFoundException("User not found with email: " + email);
//    }
//}
//
