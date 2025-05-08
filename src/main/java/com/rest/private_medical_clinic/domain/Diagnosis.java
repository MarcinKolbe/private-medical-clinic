package com.rest.private_medical_clinic.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "DIAGNOSES")
public class Diagnosis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "DIAGNOSIS_ID")
    private long id;

    @OneToOne
    @JoinColumn(name = "APPOINTMENT_ID", nullable = false)
    private Appointment appointment;

    @Column(name = "DESCRIPTION", nullable = false)
    private String description;

    @Column(name = "RECOMMENDATION")
    private String recommendation;

    @Column(name = "CREATED_AT", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "DRUG_BRAND_NAME")
    private String brand_name;

    @Column(name = "DRUG_GENERIC_NAME")
    private String generic_name;

    @Lob
    @Column(name = "DRUG_DOSAGE", columnDefinition = "TEXT")
    private String dosage_and_administration;
}
