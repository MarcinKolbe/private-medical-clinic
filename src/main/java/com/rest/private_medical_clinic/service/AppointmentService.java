package com.rest.private_medical_clinic.service;

import com.rest.private_medical_clinic.domain.Appointment;
import com.rest.private_medical_clinic.domain.Diagnosis;
import com.rest.private_medical_clinic.domain.Doctor;
import com.rest.private_medical_clinic.domain.Patient;
import com.rest.private_medical_clinic.domain.dto.AppointmentDto;
import com.rest.private_medical_clinic.domain.dto.AppointmentRegistrationDto;
import com.rest.private_medical_clinic.domain.dto.DiagnosisDto;
import com.rest.private_medical_clinic.domain.dto.OpenFdaResponseDto;
import com.rest.private_medical_clinic.enums.AppointmentStatus;
import com.rest.private_medical_clinic.exception.AppointmentNotFoundException;
import com.rest.private_medical_clinic.exception.DoctorNotFoundException;
import com.rest.private_medical_clinic.exception.PatientNotFoundException;
import com.rest.private_medical_clinic.repository.AppointmentRepository;
import com.rest.private_medical_clinic.repository.DiagnosisRepository;
import com.rest.private_medical_clinic.repository.DoctorRepository;
import com.rest.private_medical_clinic.repository.PatientRepository;
import com.rest.private_medical_clinic.validator.AppointmentValidator;
import com.rest.private_medical_clinic.validator.DiagnosisValidator;
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
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final AppointmentValidator appointmentValidator;
    private final DiagnosisValidator diagnosisValidator;
    private final DoctorAvailabilityService doctorAvailabilityService;
    private final DiagnosisRepository diagnosisRepository;
    private final OpenFdaService openFdaService;

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public List<Appointment> getAppointmentsByStatus(String status) {
        return appointmentRepository.findAllByStatus(AppointmentStatus.valueOf(status));
    }

    public Appointment getAppointmentById(long appointmentId) {
        return appointmentRepository.findById(appointmentId).orElseThrow(() -> new AppointmentNotFoundException(appointmentId));
    }

    @Transactional
    public Appointment createAppointment(AppointmentRegistrationDto appointmentRegistrationDto) {
        Doctor doctor = doctorRepository.findById(appointmentRegistrationDto.getDoctorId()).orElseThrow(
                () -> new DoctorNotFoundException(appointmentRegistrationDto.getDoctorId()));
        Patient patient = patientRepository.findById(appointmentRegistrationDto.getPatientId()).orElseThrow(
                () -> new PatientNotFoundException(appointmentRegistrationDto.getPatientId()));

        appointmentValidator.validateAvailability(doctor.getId(), appointmentRegistrationDto.getDate(), appointmentRegistrationDto.getTime());

        Appointment appointment = new Appointment();
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setDate(appointmentRegistrationDto.getDate());
        appointment.setTime(appointmentRegistrationDto.getTime());
        appointment.setStatus(AppointmentStatus.SCHEDULED);
        appointment.setNotes(appointmentRegistrationDto.getNotes());

        Appointment savedAppointment = appointmentRepository.save(appointment);

        doctorAvailabilityService.markSlotAsUnavailable(doctor.getId(), appointmentRegistrationDto.getDate(), appointmentRegistrationDto.getTime());

        return savedAppointment;
    }

    @Transactional
    public void cancelAppointment(long appointmentId) {
        Appointment appointment = getAppointmentById(appointmentId);

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
        Appointment appointment = getAppointmentById(appointmentId);

        appointmentValidator.validateAvailability(appointment.getDoctor().getId(), appointmentRequest.getDate(), appointmentRequest.getTime());

        doctorAvailabilityService.markSlotAsAvailable(appointment.getDoctor().getId(), appointment.getDate(), appointment.getTime());

        appointment.setDate(appointmentRequest.getDate());
        appointment.setTime(appointmentRequest.getTime());
        appointment.setStatus(AppointmentStatus.RESCHEDULED);

        Appointment savedAppointment = appointmentRepository.save(appointment);

        doctorAvailabilityService.markSlotAsUnavailable(savedAppointment.getDoctor().getId(), savedAppointment.getDate(), savedAppointment.getTime());

        return savedAppointment;
    }

    @Transactional
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

        diagnosisValidator.validateDiagnosis(appointmentId);

        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setAppointment(appointment);
        diagnosis.setDescription(diagnosisRequest.getDescription());
        diagnosis.setRecommendation(diagnosisRequest.getRecommendations());
        diagnosis.setCreatedAt(LocalDateTime.now());
        if (diagnosisRequest.getGeneric_name() != null) {
            OpenFdaResponseDto.DrugLabelDto drug = openFdaService.getDrug(diagnosisRequest.getGeneric_name());
            diagnosis.setGeneric_name(drug.getOpenfda().getGeneric_name().getFirst());
            diagnosisRequest.setBrand_name(drug.getOpenfda().getBrand_name().getFirst());
            diagnosisRequest.setDosage_and_administration(drug.getDosage_and_administration().getFirst());
        }
        diagnosisRepository.save(diagnosis);

        appointment.setDiagnosis(diagnosis);
        appointment.setStatus(AppointmentStatus.COMPLETED);
        appointmentRepository.save(appointment);
    }
}