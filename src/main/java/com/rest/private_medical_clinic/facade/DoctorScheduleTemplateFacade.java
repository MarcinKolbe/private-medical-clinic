package com.rest.private_medical_clinic.facade;

import com.rest.private_medical_clinic.domain.DoctorScheduleTemplate;
import com.rest.private_medical_clinic.domain.dto.DoctorScheduleTemplateDto;
import com.rest.private_medical_clinic.mapper.DoctorScheduleTemplateMapper;
import com.rest.private_medical_clinic.service.DoctorScheduleTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorScheduleTemplateFacade {

    private final DoctorScheduleTemplateService doctorScheduleTemplateService;
    private final DoctorScheduleTemplateMapper doctorScheduleTemplateMapper;

    public List<DoctorScheduleTemplateDto> getAllDoctorScheduleTemplates() {
        List<DoctorScheduleTemplate> templates = doctorScheduleTemplateService.getAllDoctorScheduleTemplate();
        return doctorScheduleTemplateMapper.mapToDtoList(templates);
    }

    public DoctorScheduleTemplateDto getDoctorScheduleTemplateById(long templateId) {
        DoctorScheduleTemplate template = doctorScheduleTemplateService.getDoctorScheduleTemplateById(templateId);
        return doctorScheduleTemplateMapper.mapToDto(template);
    }

    public void deleteDoctorScheduleTemplateById(long templateId) {
        doctorScheduleTemplateService.deleteDoctorScheduleTemplateById(templateId);
    }

    public DoctorScheduleTemplateDto createDoctorScheduleTemplate(long doctorId, DoctorScheduleTemplateDto doctorScheduleTemplateDto) {
        DoctorScheduleTemplate template = doctorScheduleTemplateService.createDoctorScheduleTemplate(doctorId, doctorScheduleTemplateDto);
        return doctorScheduleTemplateMapper.mapToDto(template);
    }

    public List<DoctorScheduleTemplateDto> getDoctorScheduleTemplatesByDoctorId(long doctorId) {
        List<DoctorScheduleTemplate> templates = doctorScheduleTemplateService.getDoctorScheduleTemplateByDoctorId(doctorId);
        return doctorScheduleTemplateMapper.mapToDtoList(templates);
    }

    public DoctorScheduleTemplateDto updateDoctorScheduleTemplate(long templateId, DoctorScheduleTemplateDto doctorScheduleTemplateDto) {
        DoctorScheduleTemplate template = doctorScheduleTemplateService.updateDoctorScheduleTemplate(templateId, doctorScheduleTemplateDto);
        return doctorScheduleTemplateMapper.mapToDto(template);
    }
}
