package com.rest.private_medical_clinic.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "REVIWES")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REVIWE_ID")
    private long id;

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

    @Column(name = "REVIEW_DATE")
    private LocalDate reviewDate;
}
