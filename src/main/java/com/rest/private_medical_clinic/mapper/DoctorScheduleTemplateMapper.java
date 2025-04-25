package com.rest.private_medical_clinic.mapper;

import com.rest.private_medical_clinic.domain.Doctor;
import com.rest.private_medical_clinic.domain.DoctorScheduleTemplate;
import com.rest.private_medical_clinic.domain.dto.DoctorScheduleTemplateDto;
import com.rest.private_medical_clinic.repository.DoctorRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class DoctorScheduleTemplateMapper {

    private final DoctorRepository doctorRepository;

    public DoctorScheduleTemplateDto mapToDto(DoctorScheduleTemplate doctorScheduleTemplate) {
        return new DoctorScheduleTemplateDto(doctorScheduleTemplate.getId(),
                doctorScheduleTemplate.getDoctor().getId(), doctorScheduleTemplate.getDayOfWeek(),
                doctorScheduleTemplate.getStartTime(), doctorScheduleTemplate.getEndTime());
    }

    public DoctorScheduleTemplate mapToEntity(DoctorScheduleTemplateDto doctorScheduleTemplateDto) {
        Optional<Doctor> doctor = doctorRepository.findById(doctorScheduleTemplateDto.getDoctorId());
        Doctor doctorEntity = doctor.orElse(null);
        return new DoctorScheduleTemplate(doctorScheduleTemplateDto.getId(), doctorEntity,
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
