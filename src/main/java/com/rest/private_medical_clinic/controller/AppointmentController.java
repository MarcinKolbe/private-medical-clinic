package com.rest.private_medical_clinic.controller;

import com.rest.private_medical_clinic.domain.Appointment;
import com.rest.private_medical_clinic.domain.dto.AppointmentDto;
import com.rest.private_medical_clinic.domain.dto.AppointmentRegistrationDto;
import com.rest.private_medical_clinic.domain.dto.DiagnosisDto;
import com.rest.private_medical_clinic.mapper.AppointmentMapper;
import com.rest.private_medical_clinic.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/appointments")
@Validated
@RequiredArgsConstructor
public class AppointmentController {

    private final AppointmentService appointmentService;
    private final AppointmentMapper appointmentMapper;
    private final Logger LOGGER = LoggerFactory.getLogger(AppointmentController.class);

    @GetMapping
    public ResponseEntity<List<AppointmentDto>> getAllAppointments(@RequestParam(required = false) String status) {
        List<Appointment> appointments = (status == null)
                ? appointmentService.getAllAppointments()
                : appointmentService.getAppointmentsByStatus(status);
        return ResponseEntity.ok(appointmentMapper.mapToDtoList(appointments));
    }

    @GetMapping("/{appointmentId}")
    public ResponseEntity<AppointmentDto> getAppointmentById(@PathVariable long appointmentId) {
        Appointment appointment = appointmentService.getAppointmentById(appointmentId);
        return ResponseEntity.ok(appointmentMapper.mapToDto(appointment));
    }

    @PostMapping(value = ("/register"), consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppointmentDto> createAppointment(@Valid @RequestBody AppointmentRegistrationDto appointmentRegistrationDto) {
        LOGGER.info("Incoming AppointmentRegistrationDto: {}", appointmentRegistrationDto);
        Appointment appointment = appointmentService.createAppointment(appointmentRegistrationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentMapper.mapToDto(appointment));
    }

    @PutMapping("/{appointmentId}/cancel")
    public ResponseEntity<Void> cancelAppointment(@PathVariable long appointmentId) {
        appointmentService.cancelAppointment(appointmentId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{appointmentId}/reschedule", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppointmentDto> rescheduleAppointment(@PathVariable long appointmentId, @Valid @RequestBody AppointmentDto appointmentDto) {
        Appointment appointment = appointmentService.rescheduleAppointment(appointmentId, appointmentDto);
        return ResponseEntity.ok(appointmentMapper.mapToDto(appointment));
    }

    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<Void> deleteAppointmentById(@PathVariable long appointmentId) {
        appointmentService.deleteAppointmentById(appointmentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<AppointmentDto>> getAppointmentsByPatientId(@PathVariable long patientId) {
        List<Appointment> appointments = appointmentService.getAppointmentsForPatient(patientId);
        return ResponseEntity.ok(appointmentMapper.mapToDtoList(appointments));
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<AppointmentDto>> getAppointmentsByDoctorId(@PathVariable long doctorId) {
        List<Appointment> appointments = appointmentService.getAppointmentsForDoctor(doctorId);
        return ResponseEntity.ok(appointmentMapper.mapToDtoList(appointments));
    }

    @GetMapping("/by-date")
    public ResponseEntity<List<AppointmentDto>> getAppointmentsByDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        List<Appointment> appointments = appointmentService.getAllAppointmentsByDate(date);
        return ResponseEntity.ok(appointmentMapper.mapToDtoList(appointments));
    }

    @PutMapping(value = "/{appointmentId}/add-diagnosis", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addDiagnosisToAppointment(@PathVariable long appointmentId, @Valid @RequestBody DiagnosisDto diagnosisDto) {
        appointmentService.addDiagnosisToAppointment(appointmentId, diagnosisDto);
        return ResponseEntity.noContent().build();
    }
}
