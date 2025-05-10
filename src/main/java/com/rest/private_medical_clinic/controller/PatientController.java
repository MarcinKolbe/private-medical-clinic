package com.rest.private_medical_clinic.controller;

import com.rest.private_medical_clinic.domain.dto.PatientDto;
import com.rest.private_medical_clinic.domain.dto.PatientRegistrationDto;
import com.rest.private_medical_clinic.facade.PatientFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/patients")
@Validated
@RequiredArgsConstructor
public class PatientController {

    private final PatientFacade patientFacade;
    private final Logger LOGGER = LoggerFactory.getLogger(PatientController.class);

    @GetMapping
    public ResponseEntity<List<PatientDto>> getAllPatients() {
        return ResponseEntity.ok(patientFacade.getAllPatients());
    }

    @GetMapping("/{patientId}")
    public ResponseEntity<PatientDto> getPatientById(@PathVariable long patientId) {
        return ResponseEntity.ok(patientFacade.getPatientById(patientId));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> registerPatient(@Valid @RequestBody PatientRegistrationDto patientRegistrationDto) {
        LOGGER.info("Incoming create request PatientRegistrationDto: {}", patientRegistrationDto);
        patientFacade.registerPatient(patientRegistrationDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PatientDto> updatePatient(@Valid @RequestBody PatientDto patientDto) {
        LOGGER.info("Incoming update request PatientDto: {}", patientDto);
        return ResponseEntity.ok(patientFacade.updatePatient(patientDto));
    }

    @DeleteMapping("/{patientId}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long patientId) {
        patientFacade.deletePatient(patientId);
        return ResponseEntity.noContent().build();
    }
}
