package com.ticgrp10.WTFCINEMA.Entities;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.YearMonth;
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

    private String role = "ROLE_USER";

    @Email(message = "Debe ser un correo válido")
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

    private long cardNumber;

    private String ownerName;

    private String expirationDate;

    private int cvv;
}


