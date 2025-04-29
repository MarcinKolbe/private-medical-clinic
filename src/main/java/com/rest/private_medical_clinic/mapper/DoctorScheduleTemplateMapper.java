package com.rest.private_medical_clinic.mapper;

import com.rest.private_medical_clinic.domain.Doctor;
import com.rest.private_medical_clinic.domain.DoctorScheduleTemplate;
import com.rest.private_medical_clinic.domain.dto.DoctorScheduleTemplateDto;
import com.rest.private_medical_clinic.repository.DoctorRepository;
import com.rest.private_medical_clinic.service.DoctorService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class DoctorScheduleTemplateMapper {

    private final DoctorRepository doctorRepository;
    private final DoctorService doctorService;

    public DoctorScheduleTemplateDto mapToDto(DoctorScheduleTemplate doctorScheduleTemplate) {
        return new DoctorScheduleTemplateDto(doctorScheduleTemplate.getId(),
                doctorScheduleTemplate.getDoctor().getId(), doctorScheduleTemplate.getDayOfWeek(),
                doctorScheduleTemplate.getStartTime(), doctorScheduleTemplate.getEndTime());
    }

    public DoctorScheduleTemplate mapToEntity(DoctorScheduleTemplateDto doctorScheduleTemplateDto) {
        Doctor doctor = doctorService.getDoctor(doctorScheduleTemplateDto.getDoctorId());
        return new DoctorScheduleTemplate(doctorScheduleTemplateDto.getId(), doctor,
                doctorScheduleTemplateDto.getDayOfWeek(), doctorScheduleTemplateDto.getStartTime(),
                doctorScheduleTemplateDto.getEndTime());
    }

    public List<DoctorScheduleTemplateDto> mapToDtoList(List<DoctorScheduleTemplate> doctorsTemplates) {
        return doctorsTemplates.stream()
                .map(this::mapToDto)
                .toList();
    }

    public List<DoctorScheduleTemplate> mapToEntityList(List<DoctorScheduleTemplateDto> doctorsTemplateDtos) {
        return doctorsTemplateDtos.stream()
                .map(this::mapToEntity)
                .toList();
    }
}
