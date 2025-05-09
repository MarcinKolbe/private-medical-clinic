package com.rest.private_medical_clinic.controller;

import com.rest.private_medical_clinic.domain.dto.DiagnosisDto;
import com.rest.private_medical_clinic.facade.DiagnosisFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/diagnoses")
@Validated
@RequiredArgsConstructor
public class DiagnosisController {

    private final DiagnosisFacade diagnosisFacade;
    private final Logger LOGGER = LoggerFactory.getLogger(DiagnosisController.class);

    @GetMapping
    public ResponseEntity<List<DiagnosisDto>> getAllDiagnoses() {
        return ResponseEntity.ok(diagnosisFacade.getAllDiagnosis());
    }

    @GetMapping("/{diagnosisId}")
    public ResponseEntity<DiagnosisDto> getDiagnosisById(@PathVariable long diagnosisId) {
        return ResponseEntity.ok(diagnosisFacade.getDiagnosisById(diagnosisId));
    }

    @DeleteMapping("/{diagnosisId}")
    public ResponseEntity<Void> deleteDiagnosisById(@PathVariable long diagnosisId) {
        diagnosisFacade.deleteDiagnosis(diagnosisId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<DiagnosisDto>> getDiagnosesByPatientId(@PathVariable long patientId) {
        return ResponseEntity.ok(diagnosisFacade.getDiagnosisByPatientId(patientId));
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<DiagnosisDto>> getDiagnosisByDoctorId(@PathVariable long doctorId) {
        return ResponseEntity.ok(diagnosisFacade.getDiagnosisByDoctorId(doctorId));
    }

    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<DiagnosisDto> getDiagnosisByAppointmentId(@PathVariable long appointmentId) {
        return ResponseEntity.ok(diagnosisFacade.getDiagnosisByAppointmentId(appointmentId));
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DiagnosisDto> updateDiagnosis(@Valid @RequestBody DiagnosisDto diagnosisDto) {
        LOGGER.info("Incoming update request DiagnosisDto: {}", diagnosisDto);
        return ResponseEntity.ok(diagnosisFacade.updateDiagnosis(diagnosisDto));
    }
}
