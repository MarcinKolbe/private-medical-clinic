package com.rest.private_medical_clinic.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientRegistrationDto {

    private String username;
    private String password;
    private String mail;
    private String firstName;
    private String lastName;
    private int phone;
    private String pesel;
    private LocalDate birthDate;
}
