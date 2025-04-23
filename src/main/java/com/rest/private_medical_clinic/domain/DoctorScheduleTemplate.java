package com.rest.private_medical_clinic.domain;

import com.rest.private_medical_clinic.enums.DayOfWeek;
import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalTime;

@Data
@Entity
@Table(name = "DOCTOR_SCHEDULE_TEMPLATE")
public class DoctorScheduleTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private long id;

    @ManyToOne
    @JoinColumn(name = "DOCTOR_ID", nullable = false)
    private Doctor doctor;

    @Column(name = "DAY_OF_WEEK", nullable = false)
    @Enumerated(EnumType.STRING)
    private DayOfWeek dayOfWeek;

    @Column(name = "START_TIME", nullable = false)
    private LocalTime startTime;

    @Column(name = "END_TIME", nullable = false)
    private LocalTime endTime;
}
