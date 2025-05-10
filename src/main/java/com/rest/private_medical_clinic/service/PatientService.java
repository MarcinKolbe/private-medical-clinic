package com.rest.private_medical_clinic.service;

import com.rest.private_medical_clinic.domain.Patient;
import com.rest.private_medical_clinic.domain.User;
import com.rest.private_medical_clinic.domain.dto.PatientDto;
import com.rest.private_medical_clinic.domain.dto.PatientRegistrationDto;
import com.rest.private_medical_clinic.enums.UserRole;
import com.rest.private_medical_clinic.exception.PatientNotFoundException;
import com.rest.private_medical_clinic.repository.PatientRepository;
import com.rest.private_medical_clinic.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;
    private final UserRepository userRepository;

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient getPatient(long id) {
        return patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException(id));
    }

    @Transactional
    public void registerPatient(PatientRegistrationDto patientRegistrationDto) {
        User user = new User();
        user.setUsername(patientRegistrationDto.getUsername());
        user.setPassword(patientRegistrationDto.getPassword());
        user.setMail(patientRegistrationDto.getMail());
        user.setUserRole(UserRole.PATIENT);
        user.setCreated_at(LocalDate.now());
        user.setBlocked(false);

        Patient patient = new Patient();
        patient.setFirstname(patientRegistrationDto.getFirstname());
        patient.setLastname(patientRegistrationDto.getLastname());
        patient.setPhoneNumber(patientRegistrationDto.getPhone());
        patient.setPesel(patientRegistrationDto.getPesel());
        patient.setBirthDate(patientRegistrationDto.getBirthDate());
        patient.setUser(user);
        user.setPatient(patient);
        userRepository.save(user);
    }

    @Transactional
    public Patient updatePatient(PatientDto patientDto) {
        Patient updatedPatient = getPatient(patientDto.getId());
        if (patientDto.getFirstname() != null) {
            updatedPatient.setFirstname(patientDto.getFirstname());
        }
        if (patientDto.getLastname() != null) {
            updatedPatient.setLastname(patientDto.getLastname());
        }
        if (patientDto.getPhoneNumber() != 0) {
            updatedPatient.setPhoneNumber(updatedPatient.getPhoneNumber());
        }
        if (patientDto.getPesel() != null) {
            updatedPatient.setPesel(patientDto.getPesel());
        }
        if (patientDto.getBirthDate() != null) {
            updatedPatient.setBirthDate(patientDto.getBirthDate());
        }
        patientRepository.save(updatedPatient);
        return updatedPatient;
    }

    @Transactional
    public void deletePatient(Long id) {
        Patient patient = getPatient(id);
        patientRepository.deleteById(patient.getId());
    }
}
