package com.rest.private_medical_clinic.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DoctorDto {

    private long id;
    private String firstname;
    private String lastname;
    private String specialization;
    private double rating;
    private long userId;
    private List<Long> appointmentIdList;
    private List<Long> reviewIdList;
}
