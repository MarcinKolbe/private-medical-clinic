package com.rest.private_medical_clinic.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
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

    @Column(name = "DESCRIPTION")
    private String description;

    @Column(name = "RECOMMENDATION")
    private String recommendation;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;
}
