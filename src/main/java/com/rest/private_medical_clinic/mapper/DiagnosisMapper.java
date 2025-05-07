package com.rest.private_medical_clinic.mapper;

import com.rest.private_medical_clinic.domain.Appointment;
import com.rest.private_medical_clinic.domain.Diagnosis;
import com.rest.private_medical_clinic.domain.dto.DiagnosisDto;
import com.rest.private_medical_clinic.service.AppointmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiagnosisMapper {

    private final AppointmentService appointmentService;

    public DiagnosisDto mapToDto(Diagnosis diagnosis) {
        return new DiagnosisDto(diagnosis.getId(), diagnosis.getAppointment().getId(), diagnosis.getDescription(),
                diagnosis.getRecommendation(), diagnosis.getCreatedAt(), diagnosis.getBrand_name(),
                diagnosis.getGeneric_name(), diagnosis.getDosage_and_administration());
    }

    public Diagnosis mapToEntity(DiagnosisDto diagnosisDto) {
        Appointment appointment = appointmentService.getAppointmentById(diagnosisDto.getAppointmentId());
        return new Diagnosis (diagnosisDto.getId(), appointment, diagnosisDto.getDescription(),
                diagnosisDto.getRecommendations(), diagnosisDto.getCreatedAt(), diagnosisDto.getBrand_name(),
                diagnosisDto.getGeneric_name(), diagnosisDto.getDosage_and_administration());
    }

    public List<DiagnosisDto> mapToDtoList(List<Diagnosis> diagnosisList) {
        return diagnosisList.stream()
                .map(this::mapToDto)
                .toList();
    }

    public List<Diagnosis> mapToEntityList(List<DiagnosisDto> diagnosisDtoList) {
        return diagnosisDtoList.stream()
                .map(this::mapToEntity)
                .toList();
    }
}
