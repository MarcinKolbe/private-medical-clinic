package com.rest.private_medical_clinic.mapper;

import com.rest.private_medical_clinic.domain.Doctor;
import com.rest.private_medical_clinic.domain.dto.DoctorDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorMapper {

    public DoctorDto mapToDto (Doctor doctor) {
        return new DoctorDto(doctor.getId(), doctor.getFirstname(), doctor.getLastname(),
                doctor.getSpecialization(), doctor.getRating());
    }

    public Doctor mapToEntity (DoctorDto doctorDto) {
         Doctor doctor = new Doctor();
         doctor.setId(doctorDto.getId());
         doctor.setFirstname(doctorDto.getFirstname());
         doctor.setLastname(doctorDto.getLastname());
         doctor.setSpecialization(doctorDto.getSpecialization());
         doctor.setRating(doctorDto.getRating());
         return doctor;
    }

    public List<DoctorDto> mapToDtoList (List<Doctor> doctors) {
        return doctors.stream()
                .map(this::mapToDto)
                .toList();
    }

    public List<Doctor> mapToEntityList (List<DoctorDto> doctorDtos) {
        return doctorDtos.stream()
                .map(this::mapToEntity)
                .toList();
    }
}
