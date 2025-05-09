package com.rest.private_medical_clinic.facade;

import com.rest.private_medical_clinic.domain.DoctorAvailability;
import com.rest.private_medical_clinic.domain.dto.DoctorAvailabilityDto;
import com.rest.private_medical_clinic.mapper.DoctorAvailabilityMapper;
import com.rest.private_medical_clinic.service.DoctorAvailabilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorAvailabilityFacade {

    private final DoctorAvailabilityService doctorAvailabilityService;
    private final DoctorAvailabilityMapper doctorAvailabilityMapper;

    public List<DoctorAvailabilityDto> getAllDoctorAvailability() {
        List<DoctorAvailability> availabilities = doctorAvailabilityService.getAllAvailabilityForAllDoctors();
        return doctorAvailabilityMapper.mapToDtoList(availabilities);
    }

    public DoctorAvailabilityDto getDoctorAvailabilityById(long availabilityId) {
        DoctorAvailability availability = doctorAvailabilityService.getDoctorAvailabilityById(availabilityId);
        return doctorAvailabilityMapper.mapToDto(availability);
    }

    public List<DoctorAvailabilityDto> getDoctorAvailabilityByDoctorId(long doctorId) {
        List<DoctorAvailability> availabilities = doctorAvailabilityService.getDoctorAvailabilityByDoctorId(doctorId);
        return doctorAvailabilityMapper.mapToDtoList(availabilities);
    }

    public List<DoctorAvailabilityDto> getAvailableSlotsForDoctor (long doctorId) {
        List<DoctorAvailability> availabilities = doctorAvailabilityService.getAvailableSlotsForDoctor(doctorId);
        return doctorAvailabilityMapper.mapToDtoList(availabilities);
    }

    public void generateDoctorAvailability(long doctorId) {
        doctorAvailabilityService.addDoctorAvailability(doctorId);
    }

    public DoctorAvailabilityDto updateDoctorAvailability(DoctorAvailabilityDto dto) {
        DoctorAvailability availability = doctorAvailabilityService.updateDoctorAvailability(dto);
        return doctorAvailabilityMapper.mapToDto(availability);
    }

    public void deleteDoctorAvailability(long availabilityId) {
        doctorAvailabilityService.deleteDoctorAvailability(availabilityId);
    }

    public boolean checkSlot(long doctorId, LocalDate date, LocalTime start, LocalTime end, String strategyKey) {
        return doctorAvailabilityService.checkSlot(doctorId, date, start, end, strategyKey);
    }
}
