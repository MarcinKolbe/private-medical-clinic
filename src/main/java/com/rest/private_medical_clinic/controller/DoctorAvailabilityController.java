package com.rest.private_medical_clinic.controller;

import com.rest.private_medical_clinic.domain.DoctorAvailability;
import com.rest.private_medical_clinic.domain.dto.DoctorAvailabilityDto;
import com.rest.private_medical_clinic.mapper.DoctorAvailabilityMapper;
import com.rest.private_medical_clinic.service.DoctorAvailabilityService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/availability")
@Validated
@RequiredArgsConstructor
public class DoctorAvailabilityController {

    private final DoctorAvailabilityService doctorAvailabilityService;
    private final DoctorAvailabilityMapper doctorAvailabilityMapper;

    @GetMapping
    public ResponseEntity<List<DoctorAvailabilityDto>> getAllDoctorAvailabilities() {
        List<DoctorAvailability> availabilities = doctorAvailabilityService.getAllAvailabilityForAllDoctors();
        return ResponseEntity.ok(doctorAvailabilityMapper.mapToDtoList(availabilities));
    }

    @GetMapping("/{availabilityId}")
    public ResponseEntity<DoctorAvailabilityDto> getDoctorAvailabilityById(@PathVariable Long availabilityId) {
        DoctorAvailability doctorAvailability = doctorAvailabilityService.getDoctorAvailabilityById(availabilityId);
        return ResponseEntity.ok(doctorAvailabilityMapper.mapToDto(doctorAvailability));
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<DoctorAvailabilityDto>> getDoctorAvailabilityByDoctorId(@PathVariable Long doctorId) {
        List<DoctorAvailability> doctorAvailabilities = doctorAvailabilityService.getDoctorAvailabilityByDoctorId(doctorId);
        return ResponseEntity.ok(doctorAvailabilityMapper.mapToDtoList(doctorAvailabilities));
    }

    @GetMapping("/{doctorId}/available-slots")
    public ResponseEntity<List<DoctorAvailabilityDto>> getDoctorAvailableSlots(@PathVariable Long doctorId) {
        List<DoctorAvailability> availableSlots = doctorAvailabilityService.getAvailableSlotsForDoctor(doctorId);
        return ResponseEntity.ok(doctorAvailabilityMapper.mapToDtoList(availableSlots));
    }

    @PostMapping("/{doctorId}")
    public ResponseEntity<Void> addDoctorAvailabilityForNext7Days(@PathVariable Long doctorId) {
        doctorAvailabilityService.addDoctorAvailability(doctorId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping
    public ResponseEntity<DoctorAvailabilityDto> updateDoctorAvailability(@Valid @RequestBody DoctorAvailabilityDto doctorAvailabilityDto) {
        DoctorAvailability doctorAvailability = doctorAvailabilityService.updateDoctorAvailability(doctorAvailabilityDto);
        return ResponseEntity.ok(doctorAvailabilityMapper.mapToDto(doctorAvailability));
    }

    @DeleteMapping("/{availabilityId}")
    public ResponseEntity<Void> deleteDoctorAvailability(@PathVariable long availabilityId) {
        doctorAvailabilityService.deleteDoctorAvailability(availabilityId);
        return ResponseEntity.noContent().build();
    }
}