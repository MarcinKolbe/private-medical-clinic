package com.rest.private_medical_clinic.controller;

import com.rest.private_medical_clinic.domain.dto.AppointmentDto;
import com.rest.private_medical_clinic.domain.dto.AppointmentRegistrationDto;
import com.rest.private_medical_clinic.domain.dto.DiagnosisDto;
import com.rest.private_medical_clinic.facade.AppointmentFacade;
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

    private final AppointmentFacade appointmentFacade;
    private final Logger LOGGER = LoggerFactory.getLogger(AppointmentController.class);

    @GetMapping
    public ResponseEntity<List<AppointmentDto>> getAllAppointments(@RequestParam(required = false) String status) {
        List<AppointmentDto> appointments = (status == null)
                ? appointmentFacade.getAllAppointments()
                : appointmentFacade.getAppointmentsByStatus(status);
        return ResponseEntity.ok(appointments);
    }

    @GetMapping("/{appointmentId}")
    public ResponseEntity<AppointmentDto> getAppointmentById(@PathVariable long appointmentId) {
        return ResponseEntity.ok(appointmentFacade.getAppointmentById(appointmentId));
    }

    @PostMapping(value = ("/register"), consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppointmentDto> createAppointment(@Valid @RequestBody AppointmentRegistrationDto appointmentRegistrationDto) {
        LOGGER.info("Incoming creating request AppointmentRegistrationDto: {}", appointmentRegistrationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(appointmentFacade.createAppointment(appointmentRegistrationDto));
    }

    @PutMapping("/{appointmentId}/cancel")
    public ResponseEntity<Void> cancelAppointment(@PathVariable long appointmentId) {
        appointmentFacade.cancelAppointment(appointmentId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping(value = "/{appointmentId}/reschedule", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AppointmentDto> rescheduleAppointment(@PathVariable long appointmentId, @Valid @RequestBody AppointmentDto appointmentDto) {
        LOGGER.info("Incoming reschedule request AppointmentDto: {}", appointmentDto);
        return ResponseEntity.ok(appointmentFacade.rescheduleAppointment(appointmentId, appointmentDto));
    }

    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<Void> deleteAppointmentById(@PathVariable long appointmentId) {
        appointmentFacade.deleteAppointment(appointmentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<AppointmentDto>> getAppointmentsByPatientId(@PathVariable long patientId) {
        return ResponseEntity.ok(appointmentFacade.getAppointmentsByPatientId(patientId));
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<AppointmentDto>> getAppointmentsByDoctorId(@PathVariable long doctorId) {
        return ResponseEntity.ok(appointmentFacade.getAppointmentsByDoctorId(doctorId));
    }

    @GetMapping("/by-date")
    public ResponseEntity<List<AppointmentDto>> getAppointmentsByDate(@RequestParam("date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
        return ResponseEntity.ok(appointmentFacade.getAppointmentsByDate(date));
    }

    @PutMapping(value = "/{appointmentId}/add-diagnosis", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> addDiagnosisToAppointment(@PathVariable long appointmentId, @Valid @RequestBody DiagnosisDto diagnosisDto) {
        LOGGER.info("Incoming DiagnosisDto: {}", diagnosisDto);
        appointmentFacade.addDiagnosisToAppointment(appointmentId, diagnosisDto);
        return ResponseEntity.noContent().build();
    }
}
