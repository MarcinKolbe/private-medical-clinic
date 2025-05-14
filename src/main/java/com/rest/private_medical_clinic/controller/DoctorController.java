package com.rest.private_medical_clinic.controller;

import com.rest.private_medical_clinic.domain.dto.DoctorDto;
import com.rest.private_medical_clinic.domain.dto.DoctorRegistrationDto;
import com.rest.private_medical_clinic.facade.DoctorFacade;
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

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/doctors")
@Validated
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorFacade doctorFacade;
    private final Logger LOGGER = LoggerFactory.getLogger(DoctorController.class);

    @GetMapping
    public ResponseEntity<List<DoctorDto>> getAllDoctors() {
        return ResponseEntity.ok(doctorFacade.getAllDoctors());
    }

    @GetMapping("/{doctorId}")
    public ResponseEntity<DoctorDto> getDoctorById(@PathVariable long doctorId) {
        return ResponseEntity.ok(doctorFacade.getDoctorById(doctorId));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> registerDoctor(@Valid @RequestBody DoctorRegistrationDto doctorRegistrationDto) {
        LOGGER.info("Incoming create request DoctorRegistrationDto: {}", doctorRegistrationDto);
        doctorFacade.registerDoctor(doctorRegistrationDto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DoctorDto> updateDoctor(@Valid @RequestBody DoctorDto doctorDto) {
        LOGGER.info("Incoming update request DoctorDto: {}", doctorDto);
        return ResponseEntity.ok(doctorFacade.updateDoctor(doctorDto));
    }

    @DeleteMapping("/{doctorId}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable long doctorId) {
        doctorFacade.deleteDoctor(doctorId);
        return ResponseEntity.noContent().build();
    }
}
