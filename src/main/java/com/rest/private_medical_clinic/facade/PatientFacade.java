package com.rest.private_medical_clinic.facade;

import com.rest.private_medical_clinic.domain.Patient;
import com.rest.private_medical_clinic.domain.dto.PatientDto;
import com.rest.private_medical_clinic.domain.dto.PatientRegistrationDto;
import com.rest.private_medical_clinic.mapper.PatientMapper;
import com.rest.private_medical_clinic.service.PatientService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatientFacade {

    private final PatientService patientService;
    private final PatientMapper patientMapper;

    public List<PatientDto> getAllPatients() {
        List<Patient> patients = patientService.getAllPatients();
        return patientMapper.mapToDtoList(patients);
    }

    public PatientDto getPatientById(long patientId) {
        Patient patient = patientService.getPatient(patientId);
        return patientMapper.mapToDto(patient);
    }

    public void registerPatient(PatientRegistrationDto patientRegistrationDto) {
        patientService.registerPatient(patientRegistrationDto);
    }

    public PatientDto updatePatient(PatientDto patientDto) {
        Patient patient = patientService.updatePatient(patientDto);
        return patientMapper.mapToDto(patient);
    }

    public void deletePatient(long patientId) {
        patientService.deletePatient(patientId);
    }
}
