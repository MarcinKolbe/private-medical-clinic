package com.rest.private_medical_clinic.validator;

import com.rest.private_medical_clinic.domain.DoctorAvailability;
import com.rest.private_medical_clinic.repository.DoctorAvailabilityRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
@RequiredArgsConstructor
public class AppointmentValidator {

    private final Logger LOGGER = LoggerFactory.getLogger(AppointmentValidator.class);
    private final DoctorAvailabilityRepository doctorAvailabilityRepository;

    public void validateAvailability(Long doctorId, LocalDate date, LocalTime startTime) {

        if (date.isBefore(LocalDate.now())) {
            LOGGER.info("At the moment it is not possible to make appointments in the past but we are working on it: E=mc^2 ;)");
            throw new IllegalStateException("Cannot schedule an appointment in the past.");
        }
        boolean exists = doctorAvailabilityRepository.findByDoctorIdAndDateAndStartTime(doctorId, date, startTime)
                .filter(DoctorAvailability::isAvailable)
                .isPresent();

        if (!exists) {
            LOGGER.info("Our doctors can't see two patients at the same time, at least not until we clone them ;)");
            throw new IllegalStateException("Doctor is not available at the selected date and time.");
        }
    }
}
