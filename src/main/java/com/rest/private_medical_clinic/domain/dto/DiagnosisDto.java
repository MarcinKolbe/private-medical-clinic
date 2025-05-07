package com.rest.private_medical_clinic.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiagnosisDto {

    private long id;
    private long appointmentId;
    private String description;
    private String recommendations;
    private LocalDateTime createdAt;
    private String brand_name;
    private String generic_name;
    private String dosage_and_administration;


}
