package com.rest.private_medical_clinic.facade;

import com.rest.private_medical_clinic.domain.Doctor;
import com.rest.private_medical_clinic.domain.dto.DoctorDto;
import com.rest.private_medical_clinic.domain.dto.DoctorRegistrationDto;
import com.rest.private_medical_clinic.mapper.DoctorMapper;
import com.rest.private_medical_clinic.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorFacade {

    private final DoctorService doctorService;
    private final DoctorMapper doctorMapper;

    public List<DoctorDto> getAllDoctors() {
        List<Doctor> doctors = doctorService.getAllDoctors();
        return doctorMapper.mapToDtoList(doctors);
    }

    public DoctorDto getDoctorById(long doctorId) {
        Doctor doctor = doctorService.getDoctor(doctorId);
        return doctorMapper.mapToDto(doctor);
    }

    public void registerDoctor(DoctorRegistrationDto doctorRegistrationDto) {
        doctorService.registerDoctor(doctorRegistrationDto);
    }

    public DoctorDto updateDoctor(DoctorDto doctorDto) {
        Doctor doctor = doctorService.updateDoctor(doctorDto);
        return doctorMapper.mapToDto(doctor);
    }

    public void deleteDoctor(long doctorId) {
        doctorService.deleteDoctor(doctorId);
    }
}
