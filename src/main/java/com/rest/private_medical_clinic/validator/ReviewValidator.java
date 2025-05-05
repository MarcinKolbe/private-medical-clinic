package com.rest.private_medical_clinic.validator;

import com.rest.private_medical_clinic.domain.Appointment;
import com.rest.private_medical_clinic.domain.dto.ReviewDto;
import com.rest.private_medical_clinic.enums.AppointmentStatus;
import com.rest.private_medical_clinic.exception.AppointmentNotFoundException;
import com.rest.private_medical_clinic.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewValidator {

    private final Logger LOGGER = LoggerFactory.getLogger(ReviewValidator.class);
    private final AppointmentRepository appointmentRepository;

    public void validateReview(ReviewDto reviewDto) {

        Appointment appointment = appointmentRepository.findById(reviewDto.getAppointmentId()).orElseThrow(
                () -> new AppointmentNotFoundException(reviewDto.getAppointmentId()));

        if (appointment.getStatus() != AppointmentStatus.COMPLETED) {
            LOGGER.warn("User {} tried to review appointment {} which is not completed",
                    appointment.getPatient().getId(), appointment.getId());
            throw new IllegalStateException("Cannot review an uncompleted appointment");
        }
        if (reviewDto.getRating() < 1 || reviewDto.getRating() > 5) {
            LOGGER.info("Review attempt with out of range rating");
            throw new IllegalStateException("Rating must be between 1 and 5");
        }
    }
}
