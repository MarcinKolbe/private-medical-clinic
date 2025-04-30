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
@Table(name = "REVIWES")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REVIWE_ID")
    private long id;

    @OneToOne
    @JoinColumn(name = "APPOINTMENT_ID")
    private Appointment appointment;

    @ManyToOne
    @JoinColumn(name = "DOCTOR_ID")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "PATIENT_ID")
    private Patient patient;

    @Column(name = "RATING")
    private int rating;

    @Column(name = "COMMENT")
    private String comment;

    @Column(name = "CREATED_AT")
    private LocalDateTime createdAt;
}
