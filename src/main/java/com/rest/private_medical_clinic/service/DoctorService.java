package com.rest.private_medical_clinic.service;

import com.rest.private_medical_clinic.domain.Doctor;
import com.rest.private_medical_clinic.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Doctor getDoctor(long doctorId) {
        return doctorRepository.findById(doctorId).orElseThrow(() -> new RuntimeException("Doctor not found"));
    }

    public Doctor saveDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public Doctor updateDoctor(Doctor doctor) {
        Doctor updatedDoctor = getDoctor(doctor.getId());
        updatedDoctor.setFirstname(doctor.getFirstname());
        updatedDoctor.setLastname(doctor.getLastname());
        updatedDoctor.setSpecialization(doctor.getSpecialization());
        return doctorRepository.save(updatedDoctor);
    }

    public void deleteDoctor(long doctorId) {
        Doctor doctor = getDoctor(doctorId);
        doctorRepository.deleteById(doctor.getId());
    }
}
