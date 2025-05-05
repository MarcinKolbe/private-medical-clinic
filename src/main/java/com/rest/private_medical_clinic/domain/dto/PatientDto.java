package com.rest.private_medical_clinic.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDto {

    private long id;
    private String firstname;
    private String lastname;
    private int phoneNumber;
    private String pesel;
    private LocalDate birthDate;
    private long userId;
    private List<Long> appointmentIdList;
    private List<Long> reviewIdList;
}
