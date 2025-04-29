package com.rest.private_medical_clinic.controller;

import com.rest.private_medical_clinic.domain.Doctor;
import com.rest.private_medical_clinic.domain.DoctorAvailability;
import com.rest.private_medical_clinic.domain.dto.DoctorAvailabilityDto;
import com.rest.private_medical_clinic.domain.dto.DoctorDto;
import com.rest.private_medical_clinic.mapper.DoctorMapper;
import com.rest.private_medical_clinic.service.DoctorAvailabilityService;
import com.rest.private_medical_clinic.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/v1/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService doctorService;
    private final DoctorMapper doctorMapper;
    private final DoctorAvailabilityService doctorAvailabilityService;

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

    @GetMapping("/{doctorId}/availability")
    public List<DoctorAvailabilityDto> getDoctorAvailability(@PathVariable Long doctorId) {
        List<DoctorAvailability> availableSlots = doctorAvailabilityService.getAvailableSlotsForDoctor(doctorId);
        return availableSlots.stream()
                .map(slot -> new DoctorAvailabilityDto(slot.getId(), slot.getDoctor().getId(),
                        slot.getDate(), slot.getStartTime(), slot.getEndTime(), slot.isAvailable()))
                .toList();
    }




}
