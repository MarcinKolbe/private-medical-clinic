package com.rest.private_medical_clinic.controller;

import com.rest.private_medical_clinic.domain.DoctorScheduleTemplate;
import com.rest.private_medical_clinic.domain.dto.DoctorScheduleTemplateDto;
import com.rest.private_medical_clinic.mapper.DoctorScheduleTemplateMapper;
import com.rest.private_medical_clinic.service.DoctorScheduleTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/schedule-template")
@RequiredArgsConstructor
public class DoctorScheduleTemplateController {

    private final DoctorScheduleTemplateService doctorScheduleTemplateService;
    private final DoctorScheduleTemplateMapper doctorScheduleTemplateMapper;

    @GetMapping
    public ResponseEntity<List<DoctorScheduleTemplateDto>> getAllTemplates() {
        List<DoctorScheduleTemplate> doctorScheduleTemplates = doctorScheduleTemplateService.getAllDoctorScheduleTemplate();
        return ResponseEntity.ok(doctorScheduleTemplateMapper.mapToDtoList(doctorScheduleTemplates));
    }

    @GetMapping("/{templateId}")
    public ResponseEntity<DoctorScheduleTemplateDto> getTemplateById(@PathVariable long templateId) {
        DoctorScheduleTemplate doctorScheduleTemplate = doctorScheduleTemplateService.getDoctorScheduleTemplateById(templateId);
        return ResponseEntity.ok(doctorScheduleTemplateMapper.mapToDto(doctorScheduleTemplate));
    }

    @DeleteMapping("/{templateId}")
    public ResponseEntity<Void> deleteTemplateById(@PathVariable long templateId) {
        DoctorScheduleTemplate doctorScheduleTemplate = doctorScheduleTemplateService.getDoctorScheduleTemplateById(templateId);
        doctorScheduleTemplateService.deleteDoctorScheduleTemplateById(doctorScheduleTemplate.getId());
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/doctor/{doctorId}")
    public ResponseEntity<DoctorScheduleTemplateDto> createTemplateForDoctor(@PathVariable long doctorId, @RequestBody DoctorScheduleTemplateDto doctorScheduleTemplateDto) {
        DoctorScheduleTemplate doctorScheduleTemplate = doctorScheduleTemplateService.createDoctorScheduleTemplate(doctorId, doctorScheduleTemplateDto);
        return ResponseEntity.ok(doctorScheduleTemplateMapper.mapToDto(doctorScheduleTemplate));
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<DoctorScheduleTemplateDto>> getTemplatesByDoctor(@PathVariable long doctorId) {
        List<DoctorScheduleTemplate> doctorScheduleTemplates = doctorScheduleTemplateService.getDoctorScheduleTemplateByDoctorId(doctorId);
        return ResponseEntity.ok(doctorScheduleTemplateMapper.mapToDtoList(doctorScheduleTemplates));
    }

    @PutMapping("/doctor/{doctorId}")
    public ResponseEntity<DoctorScheduleTemplateDto> updateTemplate(@PathVariable long doctorId, @RequestBody DoctorScheduleTemplateDto doctorScheduleTemplateDto) {
        DoctorScheduleTemplate template = doctorScheduleTemplateService.updateDoctorScheduleTemplate(doctorId, doctorScheduleTemplateDto);
        return ResponseEntity.ok(doctorScheduleTemplateMapper.mapToDto(template));
    }
}
