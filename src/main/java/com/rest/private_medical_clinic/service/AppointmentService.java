package com.rest.private_medical_clinic.service;

import com.rest.private_medical_clinic.domain.Appointment;
import com.rest.private_medical_clinic.domain.Diagnosis;
import com.rest.private_medical_clinic.domain.Doctor;
import com.rest.private_medical_clinic.domain.Patient;
import com.rest.private_medical_clinic.domain.dto.AppointmentDto;
import com.rest.private_medical_clinic.domain.dto.DiagnosisDto;
import com.rest.private_medical_clinic.enums.AppointmentStatus;
import com.rest.private_medical_clinic.exeption.AppointmentNotFoundException;
import com.rest.private_medical_clinic.repository.AppointmentRepository;
import com.rest.private_medical_clinic.validator.AppointmentValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final AppointmentRepository appointmentRepository;
    private final DoctorService doctorService;
    private final PatientService patientService;
    private final AppointmentValidator appointmentValidator;
    private final DoctorAvailabilityService doctorAvailabilityService;
    private final DiagnosisService diagnosisService;

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Appointment getAppointmentById(long appointmentId) {
        return appointmentRepository.findById(appointmentId).orElseThrow(() -> new AppointmentNotFoundException(appointmentId));
    }

    @Transactional
    public Appointment createAppointment(Appointment appointmentRequest) {
        Doctor doctor = doctorService.getDoctor(appointmentRequest.getDoctor().getId());
        Patient patient = patientService.getPatient(appointmentRequest.getPatient().getId());

        appointmentValidator.validateAvailability(doctor.getId(), appointmentRequest.getDate(), appointmentRequest.getTime());

        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setDate(appointmentRequest.getDate());
        appointment.setTime(appointmentRequest.getTime());
        appointment.setStatus(AppointmentStatus.SCHEDULED);
        appointment.setNotes(appointmentRequest.getNotes());

        Appointment savedAppointment = appointmentRepository.save(appointment);

        doctorAvailabilityService.markSlotAsUnavailable(doctor.getId(), appointmentRequest.getDate(), appointmentRequest.getTime());

        return savedAppointment;
    }

    @Transactional
    public void cancelAppointment(Long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new AppointmentNotFoundException(appointmentId));

        if (appointment.getStatus() == AppointmentStatus.CANCELLED) {
            throw new IllegalStateException("Appointment is already cancelled.");
        }

        appointment.setStatus(AppointmentStatus.CANCELLED);
        appointmentRepository.save(appointment);

        doctorAvailabilityService.markSlotAsAvailable(
                appointment.getDoctor().getId(),
                appointment.getDate(),
                appointment.getTime()
        );
    }

    @Transactional
    public Appointment rescheduleAppointment(long appointmentId, AppointmentDto appointmentRequest) {
        Appointment appointment = appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new AppointmentNotFoundException(appointmentId));

        appointmentValidator.validateAvailability(appointment.getId(), appointmentRequest.getDate(), appointmentRequest.getTime());

        doctorAvailabilityService.markSlotAsAvailable(appointment.getDoctor().getId(), appointment.getDate(), appointment.getTime());

        appointment.setDate(appointmentRequest.getDate());
        appointment.setTime(appointmentRequest.getTime());
        appointment.setStatus(AppointmentStatus.RESCHEDULED);

        Appointment savedAppointment = appointmentRepository.save(appointment);

        doctorAvailabilityService.markSlotAsUnavailable(savedAppointment.getDoctor().getId(), savedAppointment.getDate(), savedAppointment.getTime());

        return savedAppointment;
    }

    public void deleteAppointmentById(long appointmentId) {
        Appointment appointment = getAppointmentById(appointmentId);
        appointmentRepository.delete(appointment);
    }

    public List<Appointment> getAppointmentsForDoctor(long doctorId) {
        return appointmentRepository.findAllByDoctorIdOrderByDateDesc(doctorId);
    }

    public List<Appointment> getAppointmentsForPatient(long patientId) {
        return appointmentRepository.findAllByPatientIdOrderByDateDesc(patientId);
    }

    public List<Appointment> getAllAppointmentsByDate(LocalDate date) {
        return appointmentRepository.findAllByDateOrderByDateDesc(date);
    }

    @Transactional
    public void addDiagnosisToAppointment(long appointmentId, DiagnosisDto diagnosisRequest) {
        Appointment appointment = getAppointmentById(appointmentId);

        if (appointment.getStatus() == AppointmentStatus.CANCELLED) {
            throw new IllegalStateException("Cannot add diagnosis to cancelled appointment.");
        }
        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setAppointment(appointment);
        diagnosis.setDescription(diagnosisRequest.getDescription());
        diagnosis.setRecommendation(diagnosisRequest.getRecommendations());
        diagnosis.setCreatedAt(LocalDateTime.now());
        diagnosisService.addDiagnosis(diagnosis);

        appointment.setDiagnosis(diagnosis);
        appointment.setStatus(AppointmentStatus.COMPLETED);
        appointmentRepository.save(appointment);
    }
}
