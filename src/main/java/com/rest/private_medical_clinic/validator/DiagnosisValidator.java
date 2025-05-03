package com.rest.private_medical_clinic.validator;

import com.rest.private_medical_clinic.domain.Appointment;
import com.rest.private_medical_clinic.enums.AppointmentStatus;
import com.rest.private_medical_clinic.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiagnosisValidator {

    private final Logger LOGGER = LoggerFactory.getLogger(DiagnosisValidator.class);
    private final AppointmentService appointmentService;

    public void validateDiagnosis(long appointmentId) {
        Appointment appointment = appointmentService.getAppointmentById(appointmentId);

        if (appointment.getStatus().equals(AppointmentStatus.CANCELLED)) {
            LOGGER.warn("The appointment was canceled, there is no one to diagnose");
            throw new IllegalStateException("Cannot add diagnosis to cancelled appointment.");
        } else if (appointment.getStatus().equals(AppointmentStatus.COMPLETED) && appointment.getDiagnosis() != null) {
            LOGGER.info("Attempting to add another diagnosis to a completed appointment.");
            throw new IllegalStateException("Cannot add diagnosis. Appointment already has a diagnosis.");
        }
    }
}
