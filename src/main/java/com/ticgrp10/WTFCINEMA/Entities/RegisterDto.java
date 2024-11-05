package com.ticgrp10.WTFCINEMA.Entities;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class RegisterDto {

    @NotEmpty
    private String name;

    @NotEmpty
    private String surname;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private long phoneNumber;

    @NotEmpty
    private Date birthDate;

    @Size(min = 6, message = "Minimum password lenght is 6 characters")
    private String password;

    private String confirmPassword;

}
