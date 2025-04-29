package com.rest.private_medical_clinic.service;

import com.rest.private_medical_clinic.domain.Patient;
import com.rest.private_medical_clinic.exeption.PatientNotFoundException;
import com.rest.private_medical_clinic.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final PatientRepository patientRepository;

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Patient getPatient(Long id) {
        return patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException(id));
    }

    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }

    public void deletePatient(Long id) {
        Patient patient = patientRepository.findById(id).orElseThrow(() -> new PatientNotFoundException(id));
        patientRepository.deleteById(patient.getId());
    }
}
