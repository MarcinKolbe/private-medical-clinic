package com.rest.private_medical_clinic.validator;

import com.rest.private_medical_clinic.domain.Appointment;
import com.rest.private_medical_clinic.domain.dto.ReviewDto;
import com.rest.private_medical_clinic.enums.AppointmentStatus;
import com.rest.private_medical_clinic.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReviewValidator {

    private final AppointmentService appointmentService;

    public void validateReview(ReviewDto reviewDto) {

        Appointment appointment = appointmentService.getAppointmentById(reviewDto.getAppointmentId());

        if (appointment.getStatus() != AppointmentStatus.COMPLETED) {
            throw new IllegalStateException("Cannot review an uncompleted appointment");
        }
        if (reviewDto.getRating() < 1 || reviewDto.getRating() > 5) {
            throw new IllegalStateException("Rating must be between 1 and 5");
        }
    }
}
