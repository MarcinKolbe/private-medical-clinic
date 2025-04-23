package com.rest.private_medical_clinic.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Data
@Entity
@Table(name = "DOCTOR_AVAILABILITY")
public class DoctorAvailability {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "DOCTOR_ID", nullable = false)
    private Doctor doctor;

    @Column(name = "AVAILABLE_DATE")
    private LocalDate date;

    @Column(name = "START_TIME")
    private LocalTime startTime;

    @Column(name = "END_TIME")
    private LocalTime endTime;

    public List<LocalTime> getAvailableSlots(LocalTime start, LocalTime end) {
        List<LocalTime> slots = new ArrayList<>();
        LocalTime current = start;
        while (!current.plusMinutes(30).isAfter(end)) {
            slots.add(current);
            current = current.plusMinutes(30);
        }
        return slots;
    }
}
