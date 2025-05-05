package com.rest.private_medical_clinic.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorRegistrationDto {

    private String username;
    private String password;
    private String mail;
    private String firstname;
    private String lastname;
    private String specialization;
}
