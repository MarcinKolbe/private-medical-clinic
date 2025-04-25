package com.rest.private_medical_clinic.mapper;

import com.rest.private_medical_clinic.domain.Doctor;
import com.rest.private_medical_clinic.domain.DoctorAvailability;
import com.rest.private_medical_clinic.domain.dto.DoctorAvailabilityDto;
import com.rest.private_medical_clinic.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorAvailabilityMapper {

    private final DoctorService doctorService;

    public DoctorAvailabilityDto mapToDto(DoctorAvailability doctorAvailability) {
        return new DoctorAvailabilityDto(doctorAvailability.getId(), doctorAvailability.getDoctor().getId(),
                doctorAvailability.getDate(), doctorAvailability.getStartTime(), doctorAvailability.getEndTime(),
                doctorAvailability.isAvailable());
    }

    public DoctorAvailability mapToEntity(DoctorAvailabilityDto doctorAvailabilityDto) {
        Doctor doctor = doctorService.findDoctorById(doctorAvailabilityDto.getDoctorId());
        return new DoctorAvailability(doctorAvailabilityDto.getId(), doctor, doctorAvailabilityDto.getDate(),
                doctorAvailabilityDto.getStartTime(), doctorAvailabilityDto.getEndTime(),
                doctorAvailabilityDto.isAvailable());
    }

    public List<DoctorAvailabilityDto> mapToDtoList(List<DoctorAvailability> availabilities) {
        return availabilities.stream()
                .map(this::mapToDto)
                .toList();
    }

    public List<DoctorAvailability> mapToEntityList(List<DoctorAvailabilityDto> availabilitiesDtos) {
        return availabilitiesDtos.stream()
                .map(this::mapToEntity)
                .toList();
    }
}
