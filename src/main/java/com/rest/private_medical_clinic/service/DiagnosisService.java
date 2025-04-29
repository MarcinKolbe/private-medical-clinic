package com.rest.private_medical_clinic.service;

import com.rest.private_medical_clinic.domain.Appointment;
import com.rest.private_medical_clinic.domain.Diagnosis;
import com.rest.private_medical_clinic.domain.Doctor;
import com.rest.private_medical_clinic.domain.Patient;
import com.rest.private_medical_clinic.exeption.DiagnosisNotFoundException;
import com.rest.private_medical_clinic.repository.DiagnosisRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiagnosisService {

    private final DiagnosisRepository diagnosisRepository;
    private final PatientService patientService;
    private final DoctorService doctorService;
    private final AppointmentService appointmentService;

    public List<Diagnosis> getAllDiagnosis() {
        return diagnosisRepository.findAll();
    }

    public Diagnosis getDiagnosisById(long diagnosisId) {
        return diagnosisRepository.findById(diagnosisId).orElseThrow(
                () -> new DiagnosisNotFoundException(diagnosisId));
    }

    @Transactional
    public Diagnosis addDiagnosis(Diagnosis diagnosis) {
        return diagnosisRepository.save(diagnosis);
    }

    @Transactional
    public void deleteDiagnosisById(long diagnosisId) {
        Diagnosis diagnosis = getDiagnosisById(diagnosisId);
        diagnosisRepository.delete(diagnosis);
    }

    public List<Diagnosis> getDiagnosisByPatientId(long patientId) {
        Patient patient = patientService.getPatient(patientId);
        return diagnosisRepository.findByAppointment_PatientId(patient.getId());
    }

    public List<Diagnosis> getDiagnosisByDoctorId(long doctorId) {
        Doctor doctor = doctorService.getDoctor(doctorId);
        return diagnosisRepository.findByAppointment_DoctorId(doctor.getId());
    }

    public Diagnosis getDiagnosisByAppointmentId(long appointmentId) {
        Appointment appointment = appointmentService.getAppointmentById(appointmentId);
        return diagnosisRepository.findByAppointment_appointmentId(appointment.getId());
    }
}
