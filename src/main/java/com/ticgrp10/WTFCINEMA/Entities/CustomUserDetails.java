//package com.ticgrp10.WTFCINEMA.Entities;
//
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//import java.util.Collections;
//
//public class CustomUserDetails implements UserDetails {
//
//    private final String username;
//    private final String password;
//    private final Collection<? extends GrantedAuthority> authorities;
//
//    public CustomUserDetails(WebUser user) {
//        this.username = user.getEmail();
//        this.password = user.getPassword();
//        this.authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_USER"));
//    }
//
//    public CustomUserDetails(Admin admin) {
//        this.username = admin.getEmail();
//        this.password = admin.getPassword();
//        this.authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
//    }
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        return authorities;
//    }
//
//    @Override
//    public String getPassword() {
//        return password;
//    }
//
//    @Override
//    public String getUsername() {
//        return username;
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//}
//
