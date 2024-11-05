package com.ticgrp10.WTFCINEMA.Entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

import java.util.Date;

@Getter //@Data
@Setter
@Entity
@Table(name = "Users")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class WebUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (unique = true, nullable = false)
    private long id;

    @NotBlank(message = "error")
    private String name;

    @NotBlank(message = "error")
    private String surname;

    @Email(message = "Email should be valid")
    @Column(unique = true, nullable = false)
    protected String email;

    @NotNull
    @Temporal(TemporalType.DATE)
    protected Date birthDate;

    @NotBlank(message = "error")
    @Column(nullable = false)
    protected String password;

    @NotNull
    private long phoneNumber;

    //@NotNull
    private long cardNumber;

    //@NotNull
    private String ownerName;

    //@NotNull
    private Date expirationDate;

    //@NotNull
    private int cvv;
}



    //constructor

   //public User(String password, String email, Date birthDate) {
   //    this.password = password;
   //    this.email = email;
   //    this.birthDate = birthDate;
   //    this.userBookings = new ArrayList<>();
   //}
   // public User(String password, String email, Date birthDate, String userType) {
   //     this.password = password;
   //     this.email = email;
   //     this.birthDate = birthDate;
   //     this.userBookings = new ArrayList<>();
   //     this.userType = userType;
   // }


    //@Override
    //public String toString() {
    //    return "User{" +
    //            "id=" + id +
    //            ", password='" + password + '\'' +
    //            ", email='" + email + '\'' +
    //            ", userBookings=" + userBookings +
    //            ", birthDate=" + birthDate +
    //            '}';
    //}


