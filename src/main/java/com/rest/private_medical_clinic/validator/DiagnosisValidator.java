package com.rest.private_medical_clinic.validator;

import com.rest.private_medical_clinic.domain.Appointment;
import com.rest.private_medical_clinic.enums.AppointmentStatus;
import com.rest.private_medical_clinic.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DiagnosisValidator {

    private final AppointmentService appointmentService;

    public void validateDiagnosis(long appointmentId) {
        Appointment appointment = appointmentService.getAppointmentById(appointmentId);

        if (appointment.getStatus().equals(AppointmentStatus.CANCELLED)) {
            throw new IllegalStateException("Cannot add diagnosis to cancelled appointment.");
        } else if (appointment.getStatus().equals(AppointmentStatus.COMPLETED) && appointment.getDiagnosis() != null) {
            throw new IllegalStateException("Appointment has been completed and has a diagnosis.");
        }
    }
}
