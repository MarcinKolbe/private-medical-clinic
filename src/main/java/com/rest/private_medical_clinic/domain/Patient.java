package com.rest.private_medical_clinic.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "PATIENTS")
public class Patient {

    @Id
    private long id;

    @Column(name = "FIRSTNAME", nullable = false)
    private String firstName;

    @Column(name = "LASTNAME", nullable = false)
    private String lastName;

    @Column(name = "PHONE_NUMBER", nullable = false)
    private int phoneNumber;

    @Column(name = "PESEL", nullable = false)
    private String pesel;

    @OneToOne
    @MapsId
    @JoinColumn(name = "ID")
    private User user;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Appointment> appointmentList;

}
