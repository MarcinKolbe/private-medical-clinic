package com.rest.private_medical_clinic.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentRegistrationDto {

    private long doctorId;
    private long patientId;
    private LocalDate date;
    private LocalTime time;
    private String notes;
}
