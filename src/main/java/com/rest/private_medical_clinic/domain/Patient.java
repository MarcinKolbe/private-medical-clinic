package com.rest.private_medical_clinic.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "PATIENTS")
public class Patient {

    @Id
    private long id;

    @Column(name = "FIRSTNAME", nullable = false)
    private String firstname;

    @Column(name = "LASTNAME", nullable = false)
    private String lastname;

    @Column(name = "PHONE_NUMBER", nullable = false)
    private int phoneNumber;

    @Column(name = "PESEL", nullable = false)
    private String pesel;

    @Column(name = "BIRTH_DATE", nullable = false)
    private LocalDate birthDate;

    @OneToOne
    @MapsId
    @JoinColumn(name = "ID", nullable = false)
    private User user;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Appointment> appointmentList;

    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Review> reviewList;

}
