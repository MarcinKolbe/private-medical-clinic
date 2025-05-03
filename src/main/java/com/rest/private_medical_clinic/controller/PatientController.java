package com.rest.private_medical_clinic.controller;

import com.rest.private_medical_clinic.domain.Patient;
import com.rest.private_medical_clinic.domain.dto.PatientDto;
import com.rest.private_medical_clinic.domain.dto.PatientRegistrationDto;
import com.rest.private_medical_clinic.mapper.PatientMapper;
import com.rest.private_medical_clinic.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/patients")
@Validated
@RequiredArgsConstructor
public class PatientController {

    private final PatientService patientService;
    private final PatientMapper patientMapper;

    @GetMapping
    public ResponseEntity<List<PatientDto>> getAllPatients() {
        List<Patient> patients = patientService.getAllPatients();
        return ResponseEntity.ok(patientMapper.mapToDtoList(patients));
    }

    @GetMapping("/{patientId}")
    public ResponseEntity<PatientDto> getPatientById(@PathVariable Long patientId) {
        Patient patient = patientService.getPatient(patientId);
        return ResponseEntity.ok(patientMapper.mapToDto(patient));
    }

    @PostMapping
    public ResponseEntity<Void> registerPatient(@Valid @RequestBody PatientRegistrationDto patientRegistrationDto) {
        patientService.registerPatient(patientRegistrationDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<PatientDto> updatePatient(@Valid @RequestBody PatientDto patientDto) {
        Patient patient = patientService.updatePatient(patientDto);
        return ResponseEntity.ok(patientMapper.mapToDto(patient));
    }

    @DeleteMapping("/{patientId}")
    public ResponseEntity<Void> deletePatient(@PathVariable Long patientId) {
        patientService.deletePatient(patientId);
        return ResponseEntity.noContent().build();
    }
}
