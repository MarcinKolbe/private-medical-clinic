package com.rest.private_medical_clinic.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "DOCTORS")
public class Doctor {

    @Id
    private long id;

    @Column(name = "FIRSTNAME", nullable = false)
    private String firstname;

    @Column(name = "LASTNAME", nullable = false)
    private String lastname;

    @Column(name = "SPECIALIZATION", nullable = false)
    private String specialization;

    @Column(name = "RATING")
    private double rating;

    @OneToOne
    @MapsId
    @JoinColumn(name = "ID")
    private User user;

    @OneToMany(mappedBy = "doctor", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Appointment> appointmentList;
}
