package com.rest.private_medical_clinic.domain.dto;

import com.rest.private_medical_clinic.enums.AppointmentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppointmentDto {

    private long id;
    private long doctorId;
    private long patientId;
    private LocalDate date;
    private LocalTime time;
    private AppointmentStatus status;
    private String notes;
    private long diagnoseId;
}
