package com.rest.private_medical_clinic.validator;

import com.rest.private_medical_clinic.domain.DoctorAvailability;
import com.rest.private_medical_clinic.repository.DoctorAvailabilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;

@Service
@RequiredArgsConstructor
public class AppointmentValidator {

    private final DoctorAvailabilityRepository doctorAvailabilityRepository;

    public void validateAvailability(Long doctorId, LocalDate date, LocalTime startTime) {

        if (date.isBefore(LocalDate.now())) {
            throw new IllegalStateException("Cannot schedule an appointment in the past.");
        }
        boolean exists = doctorAvailabilityRepository.findByDoctorIdAndDateAndStartTime(doctorId, date, startTime)
                .filter(DoctorAvailability::isAvailable)
                .isPresent();

        if (!exists) {
            throw new IllegalStateException("Doctor is not available at the selected date and time.");
        }
    }
}
