package com.rest.private_medical_clinic.facade;

import com.rest.private_medical_clinic.domain.Appointment;
import com.rest.private_medical_clinic.domain.dto.AppointmentDto;
import com.rest.private_medical_clinic.domain.dto.AppointmentRegistrationDto;
import com.rest.private_medical_clinic.domain.dto.AppointmentRescheduleDto;
import com.rest.private_medical_clinic.domain.dto.DiagnosisDto;
import com.rest.private_medical_clinic.mapper.AppointmentMapper;
import com.rest.private_medical_clinic.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentFacade {

    private final AppointmentService appointmentService;
    private final AppointmentMapper appointmentMapper;

    public List<AppointmentDto> getAllAppointments() {
        List<Appointment> appointments = appointmentService.getAllAppointments();
        return appointmentMapper.mapToDtoList(appointments);
    }

    public List<AppointmentDto> getAppointmentsByStatus (String status) {
        List<Appointment> appointments = appointmentService.getAppointmentsByStatus(status);
        return appointmentMapper.mapToDtoList(appointments);
    }

    public AppointmentDto getAppointmentById(long id) {
        Appointment appointment = appointmentService.getAppointmentById(id);
        return appointmentMapper.mapToDto(appointment);
    }

    public AppointmentDto createAppointment(AppointmentRegistrationDto appointmentRegistrationDto) {
        Appointment appointment = appointmentService.createAppointment(appointmentRegistrationDto);
        return appointmentMapper.mapToDto(appointment);
    }

    public void cancelAppointment(long appointmentId) {
        appointmentService.cancelAppointment(appointmentId);
    }

    public AppointmentDto rescheduleAppointment(long appointmentId, AppointmentRescheduleDto appointmentRescheduleDto) {
        Appointment appointment = appointmentService.rescheduleAppointment(appointmentId, appointmentRescheduleDto);
        return appointmentMapper.mapToDto(appointment);
    }

    public void deleteAppointment(long appointmentId) {
        appointmentService.deleteAppointmentById(appointmentId);
    }

    public List<AppointmentDto> getAppointmentsByPatientId(long patientId) {
        List<Appointment> appointments = appointmentService.getAppointmentsForPatient(patientId);
        return appointmentMapper.mapToDtoList(appointments);
    }

    public List<AppointmentDto> getAppointmentsByDoctorId(long doctorId) {
        List<Appointment> appointments = appointmentService.getAppointmentsForDoctor(doctorId);
        return appointmentMapper.mapToDtoList(appointments);
    }

    public List<AppointmentDto> getAppointmentsByDate(LocalDate date) {
        List<Appointment> appointments = appointmentService.getAllAppointmentsByDate(date);
        return appointmentMapper.mapToDtoList(appointments);
    }

    public void addDiagnosisToAppointment(long appointmentId, DiagnosisDto diagnosisDto) {
        appointmentService.addDiagnosisToAppointment(appointmentId, diagnosisDto);
    }
}
