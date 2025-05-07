package com.rest.private_medical_clinic.service;

import com.rest.private_medical_clinic.domain.Appointment;
import com.rest.private_medical_clinic.domain.Diagnosis;
import com.rest.private_medical_clinic.domain.Doctor;
import com.rest.private_medical_clinic.domain.Patient;
import com.rest.private_medical_clinic.domain.dto.DiagnosisDto;
import com.rest.private_medical_clinic.domain.dto.OpenFdaResponseDto;
import com.rest.private_medical_clinic.exception.AppointmentNotFoundException;
import com.rest.private_medical_clinic.exception.DiagnosisNotFoundException;
import com.rest.private_medical_clinic.exception.DoctorNotFoundException;
import com.rest.private_medical_clinic.exception.PatientNotFoundException;
import com.rest.private_medical_clinic.repository.AppointmentRepository;
import com.rest.private_medical_clinic.repository.DiagnosisRepository;
import com.rest.private_medical_clinic.repository.DoctorRepository;
import com.rest.private_medical_clinic.repository.PatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiagnosisService {

    private final DiagnosisRepository diagnosisRepository;
    private final PatientRepository patientRepository;
    private final DoctorRepository doctorRepository;
    private final AppointmentRepository appointmentRepository;
    private final OpenFdaService openFdaService;

    public List<Diagnosis> getAllDiagnosis() {
        return diagnosisRepository.findAll();
    }

    public Diagnosis getDiagnosisById(long diagnosisId) {
        return diagnosisRepository.findById(diagnosisId).orElseThrow(
                () -> new DiagnosisNotFoundException(diagnosisId));
    }

    @Transactional
    public void deleteDiagnosisById(long diagnosisId) {
        Diagnosis diagnosis = getDiagnosisById(diagnosisId);
        diagnosisRepository.delete(diagnosis);
    }

    public List<Diagnosis> getDiagnosisByPatientId(long patientId) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(() -> new PatientNotFoundException(patientId));
        return diagnosisRepository.findByAppointment_PatientId(patient.getId());
    }

    public List<Diagnosis> getDiagnosisByDoctorId(long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new DoctorNotFoundException(doctorId));
        return diagnosisRepository.findByAppointment_DoctorId(doctor.getId());
    }

    public Diagnosis getDiagnosisByAppointmentId(long appointmentId) {
        Appointment appointment = appointmentRepository.findById(appointmentId).orElseThrow(() -> new AppointmentNotFoundException(appointmentId));
        return diagnosisRepository.findByAppointment_Id(appointment.getId());
    }

    public Diagnosis updateDiagnosis(DiagnosisDto diagnosisDto) {
        Diagnosis diagnosis = getDiagnosisById(diagnosisDto.getId());

        if (diagnosisDto.getDescription() != null) {
            diagnosis.setDescription(diagnosisDto.getDescription());
        }
        if (diagnosisDto.getRecommendations() != null) {
            diagnosis.setRecommendation(diagnosisDto.getRecommendations());
        }
        if (diagnosisDto.getGeneric_name() != null) {
            OpenFdaResponseDto.DrugLabelDto drug = openFdaService.getDrug(diagnosisDto.getGeneric_name());
            diagnosis.setGeneric_name(drug.getOpenfda().getGeneric_name().getFirst());
            diagnosis.setBrand_name(drug.getOpenfda().getBrand_name().getFirst());
            diagnosis.setDosage_and_administration(drug.getDosage_and_administration().getFirst());
        }
        return diagnosisRepository.save(diagnosis);
    }
}
