package com.rest.private_medical_clinic.controller;

import com.rest.private_medical_clinic.domain.dto.DoctorAvailabilityDto;
import com.rest.private_medical_clinic.facade.DoctorAvailabilityFacade;
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
import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/v1/availability")
@Validated
@RequiredArgsConstructor
public class DoctorAvailabilityController {

    private final DoctorAvailabilityFacade doctorAvailabilityFacade;
    private final Logger LOGGER = LoggerFactory.getLogger(DoctorAvailabilityController.class);

    @GetMapping
    public ResponseEntity<List<DoctorAvailabilityDto>> getAllDoctorAvailabilities() {
        return ResponseEntity.ok(doctorAvailabilityFacade.getAllDoctorAvailability());
    }

    @GetMapping("/{availabilityId}")
    public ResponseEntity<DoctorAvailabilityDto> getDoctorAvailabilityById(@PathVariable Long availabilityId) {
        return ResponseEntity.ok(doctorAvailabilityFacade.getDoctorAvailabilityById(availabilityId));
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<DoctorAvailabilityDto>> getDoctorAvailabilityByDoctorId(@PathVariable Long doctorId) {
        return ResponseEntity.ok(doctorAvailabilityFacade.getDoctorAvailabilityByDoctorId(doctorId));
    }

    @GetMapping("/{doctorId}/available-slots")
    public ResponseEntity<List<DoctorAvailabilityDto>> getDoctorAvailableSlots(@PathVariable Long doctorId) {
        return ResponseEntity.ok(doctorAvailabilityFacade.getAvailableSlotsForDoctor(doctorId));
    }

    @PostMapping("/{doctorId}")
    public ResponseEntity<Void> addDoctorAvailabilityForNext7Days(@PathVariable Long doctorId) {
        doctorAvailabilityFacade.generateDoctorAvailability(doctorId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DoctorAvailabilityDto> updateDoctorAvailability(@Valid @RequestBody DoctorAvailabilityDto doctorAvailabilityDto) {
        LOGGER.info("Incoming update request DoctorAvailabilityDto: {}", doctorAvailabilityDto);
        return ResponseEntity.ok(doctorAvailabilityFacade.updateDoctorAvailability(doctorAvailabilityDto));
    }

    @DeleteMapping("/{availabilityId}")
    public ResponseEntity<Void> deleteDoctorAvailability(@PathVariable long availabilityId) {
        doctorAvailabilityFacade.deleteDoctorAvailability(availabilityId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/check")
    public ResponseEntity<Void> check(
            @RequestParam long doctorId,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime start,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.TIME) LocalTime end,
            @RequestParam(required = false, defaultValue = "defaultStrategy") String strategy) {

        boolean ok = doctorAvailabilityFacade.checkSlot(doctorId, date, start,end, strategy);

        return ok ? ResponseEntity.ok().build() : ResponseEntity.status(HttpStatus.CONFLICT).build();
    }
}