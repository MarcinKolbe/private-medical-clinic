package com.rest.private_medical_clinic.strategy;

import com.rest.private_medical_clinic.domain.Doctor;

import java.time.LocalDate;
import java.time.LocalTime;

public interface AvailabilityStrategy {
    boolean isSlotAvailable(Doctor doctor, LocalDate date, LocalTime start, LocalTime end);
}
