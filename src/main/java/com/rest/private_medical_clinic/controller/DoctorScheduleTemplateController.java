package com.rest.private_medical_clinic.controller;

import com.rest.private_medical_clinic.domain.dto.DoctorScheduleTemplateDto;
import com.rest.private_medical_clinic.facade.DoctorScheduleTemplateFacade;
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
@RequestMapping("/v1/schedule-template")
@Validated
@RequiredArgsConstructor
public class DoctorScheduleTemplateController {

    private final DoctorScheduleTemplateFacade doctorScheduleTemplateFacade;
    private final Logger LOGGER = LoggerFactory.getLogger(DoctorScheduleTemplateController.class);

    @GetMapping
    public ResponseEntity<List<DoctorScheduleTemplateDto>> getAllTemplates() {
        return ResponseEntity.ok(doctorScheduleTemplateFacade.getAllDoctorScheduleTemplates());
    }

    @GetMapping("/{templateId}")
    public ResponseEntity<DoctorScheduleTemplateDto> getTemplateById(@PathVariable long templateId) {
        return ResponseEntity.ok(doctorScheduleTemplateFacade.getDoctorScheduleTemplateById(templateId));
    }

    @DeleteMapping("/{templateId}")
    public ResponseEntity<Void> deleteTemplateById(@PathVariable long templateId) {
        doctorScheduleTemplateFacade.deleteDoctorScheduleTemplateById(templateId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/doctor/{doctorId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DoctorScheduleTemplateDto> createTemplateForDoctor(@PathVariable long doctorId, @Valid @RequestBody DoctorScheduleTemplateDto doctorScheduleTemplateDto) {
        LOGGER.info("Incoming create request DoctorScheduleTemplateDto: {}", doctorScheduleTemplateDto);
        return ResponseEntity.ok(doctorScheduleTemplateFacade.createDoctorScheduleTemplate(doctorId, doctorScheduleTemplateDto));
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<DoctorScheduleTemplateDto>> getTemplatesByDoctor(@PathVariable long doctorId) {
        return ResponseEntity.ok(doctorScheduleTemplateFacade.getDoctorScheduleTemplatesByDoctorId(doctorId));
    }

    @PutMapping(value = "/{templateId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<DoctorScheduleTemplateDto> updateTemplate(@PathVariable long templateId, @Valid @RequestBody DoctorScheduleTemplateDto doctorScheduleTemplateDto) {
        LOGGER.info("Incoming update request DoctorScheduleTemplateDto: {}", doctorScheduleTemplateDto);
        return ResponseEntity.ok(doctorScheduleTemplateFacade.updateDoctorScheduleTemplate(templateId, doctorScheduleTemplateDto));
    }
}
