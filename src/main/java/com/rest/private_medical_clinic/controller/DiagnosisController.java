package com.rest.private_medical_clinic.controller;

import com.rest.private_medical_clinic.domain.Diagnosis;
import com.rest.private_medical_clinic.domain.dto.DiagnosisDto;
import com.rest.private_medical_clinic.mapper.DiagnosisMapper;
import com.rest.private_medical_clinic.service.DiagnosisService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/diagnoses")
@RequiredArgsConstructor
public class DiagnosisController {

    private final DiagnosisService diagnosisService;
    private final DiagnosisMapper diagnosisMapper;

    @GetMapping
    public ResponseEntity<List<DiagnosisDto>> getAllDiagnoses() {
        List<Diagnosis> diagnoses = diagnosisService.getAllDiagnosis();
        return ResponseEntity.ok(diagnosisMapper.mapToDtoList(diagnoses));
    }

    @GetMapping("/{diagnosisId}")
    public ResponseEntity<DiagnosisDto> getDiagnosisById(@PathVariable long diagnosisId) {
        Diagnosis diagnosis = diagnosisService.getDiagnosisById(diagnosisId);
        return ResponseEntity.ok(diagnosisMapper.mapToDto(diagnosis));
    }

    @DeleteMapping("/{diagnosisId}")
    public ResponseEntity<Void> deleteDiagnosisById(@PathVariable long diagnosisId) {
        diagnosisService.deleteDiagnosisById(diagnosisId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<DiagnosisDto>> getDiagnosesByPatientId(@PathVariable long patientId) {
        List<Diagnosis> diagnoses = diagnosisService.getDiagnosisByPatientId(patientId);
        return ResponseEntity.ok(diagnosisMapper.mapToDtoList(diagnoses));
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<DiagnosisDto>> getDiagnosisByDoctorId(@PathVariable long doctorId) {
        List<Diagnosis> diagnoses = diagnosisService.getDiagnosisByDoctorId(doctorId);
        return ResponseEntity.ok(diagnosisMapper.mapToDtoList(diagnoses));
    }

    @GetMapping("/appointment/{appointmentId}")
    public ResponseEntity<DiagnosisDto> getDiagnosisByAppointmentId(@PathVariable long appointmentId) {
        Diagnosis diagnosis = diagnosisService.getDiagnosisByAppointmentId(appointmentId);
        return ResponseEntity.ok(diagnosisMapper.mapToDto(diagnosis));
    }

    @PutMapping
    public ResponseEntity<DiagnosisDto> updateDiagnosis(@RequestBody DiagnosisDto diagnosisDto) {
        Diagnosis diagnosis = diagnosisService.updateDiagnosis(diagnosisDto);
        return ResponseEntity.ok(diagnosisMapper.mapToDto(diagnosis));
    }
}
