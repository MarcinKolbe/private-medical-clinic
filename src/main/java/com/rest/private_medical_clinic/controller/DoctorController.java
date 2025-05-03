package com.rest.private_medical_clinic.controller;

import com.rest.private_medical_clinic.domain.Doctor;
import com.rest.private_medical_clinic.domain.dto.DoctorDto;
import com.rest.private_medical_clinic.domain.dto.DoctorRegistrationDto;
import com.rest.private_medical_clinic.mapper.DoctorMapper;
import com.rest.private_medical_clinic.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/doctors")
@Validated
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;
    private final DoctorMapper doctorMapper;

    @GetMapping
    public ResponseEntity<List<DoctorDto>> getAllDoctors() {
        List<Doctor> doctors = doctorService.getAllDoctors();
        return ResponseEntity.ok(doctorMapper.mapToDtoList(doctors));
    }

    @GetMapping("/{doctorId}")
    public ResponseEntity<DoctorDto> getDoctorById(@PathVariable Long doctorId) {
        Doctor doctor = doctorService.getDoctor(doctorId);
        return ResponseEntity.ok(doctorMapper.mapToDto(doctor));
    }

    @PostMapping
    public ResponseEntity<Void> registerDoctor(@Valid @RequestBody DoctorRegistrationDto doctorRegistrationDto) {
        doctorService.registerDoctor(doctorRegistrationDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<DoctorDto> updateDoctor(@Valid @RequestBody DoctorDto doctorDto) {
        Doctor doctor = doctorService.updateDoctor(doctorDto);
        return ResponseEntity.ok(doctorMapper.mapToDto(doctor));
    }

    @DeleteMapping("/{doctorId}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable long doctorId) {
        doctorService.deleteDoctor(doctorId);
        return ResponseEntity.noContent().build();
    }
}
