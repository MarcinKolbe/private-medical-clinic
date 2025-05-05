package com.rest.private_medical_clinic.validator;

import com.rest.private_medical_clinic.domain.Appointment;
import com.rest.private_medical_clinic.enums.AppointmentStatus;
import com.rest.private_medical_clinic.exception.AppointmentNotFoundException;
import com.rest.private_medical_clinic.repository.AppointmentRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DiagnosisValidator {

    private final Logger LOGGER = LoggerFactory.getLogger(DiagnosisValidator.class);
    private final AppointmentRepository appointmentRepository;

    public void validateDiagnosis(long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() -> new AppointmentNotFoundException(appointmentId));

        if (appointment.getStatus().equals(AppointmentStatus.CANCELLED)) {
            LOGGER.warn("The appointment was canceled, there is no one to diagnose");
            throw new IllegalStateException("Cannot add diagnosis to cancelled appointment.");
        } else if (appointment.getStatus().equals(AppointmentStatus.COMPLETED) && appointment.getDiagnosis() != null) {
            LOGGER.info("Attempting to add another diagnosis to a completed appointment.");
            throw new IllegalStateException("Cannot add diagnosis. Appointment already has a diagnosis.");
        }
    }
}
