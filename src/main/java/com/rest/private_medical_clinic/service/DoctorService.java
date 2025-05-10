package com.rest.private_medical_clinic.service;

import com.rest.private_medical_clinic.domain.Doctor;
import com.rest.private_medical_clinic.domain.User;
import com.rest.private_medical_clinic.domain.dto.DoctorDto;
import com.rest.private_medical_clinic.domain.dto.DoctorRegistrationDto;
import com.rest.private_medical_clinic.enums.UserRole;
import com.rest.private_medical_clinic.exception.DoctorNotFoundException;
import com.rest.private_medical_clinic.repository.DoctorRepository;
import com.rest.private_medical_clinic.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final DoctorRepository doctorRepository;
    private final UserRepository userRepository;

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Doctor getDoctor(long doctorId) {
        return doctorRepository.findById(doctorId).orElseThrow(() -> new DoctorNotFoundException(doctorId));
    }

    @Transactional
    public void registerDoctor(DoctorRegistrationDto doctorRegistrationDto) {
        User user = new User();
        user.setUsername(doctorRegistrationDto.getUsername());
        user.setPassword(doctorRegistrationDto.getPassword());
        user.setMail(doctorRegistrationDto.getMail());
        user.setUserRole(UserRole.DOCTOR);
        user.setCreated_at(LocalDate.now());
        user.setBlocked(false);

        Doctor doctor = new Doctor();
        doctor.setFirstname(doctorRegistrationDto.getFirstname());
        doctor.setLastname(doctorRegistrationDto.getLastname());
        doctor.setSpecialization(doctorRegistrationDto.getSpecialization());
        doctor.setUser(user);
        user.setDoctor(doctor);
        userRepository.save(user);
    }

    @Transactional
    public Doctor updateDoctor(DoctorDto doctorDto) {
        Doctor updatedDoctor = getDoctor(doctorDto.getId());
        if (doctorDto.getFirstname() != null) {
            updatedDoctor.setFirstname(doctorDto.getFirstname());
        }
        if (doctorDto.getLastname() != null) {
            updatedDoctor.setLastname(doctorDto.getLastname());
        }
        if (doctorDto.getSpecialization() != null) {
            updatedDoctor.setSpecialization(doctorDto.getSpecialization());
        }
        doctorRepository.save(updatedDoctor);
        return updatedDoctor;
    }

    @Transactional
    public void deleteDoctor(long doctorId) {
        Doctor doctor = getDoctor(doctorId);
        doctorRepository.deleteById(doctor.getId());
    }
}
