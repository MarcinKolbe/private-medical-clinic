package com.rest.private_medical_clinic.facade;

import com.rest.private_medical_clinic.domain.Diagnosis;
import com.rest.private_medical_clinic.domain.dto.DiagnosisDto;
import com.rest.private_medical_clinic.mapper.DiagnosisMapper;
import com.rest.private_medical_clinic.service.DiagnosisService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DiagnosisFacade {

    private final DiagnosisService diagnosisService;
    private final DiagnosisMapper diagnosisMapper;

    public List<DiagnosisDto> getAllDiagnosis() {
        List<Diagnosis> diagnosisList = diagnosisService.getAllDiagnosis();
        return diagnosisMapper.mapToDtoList(diagnosisList);
    }

    public DiagnosisDto getDiagnosisById(long diagnosisId) {
        Diagnosis diagnosis = diagnosisService.getDiagnosisById(diagnosisId);
        return diagnosisMapper.mapToDto(diagnosis);
    }

    public void deleteDiagnosis(long diagnosisId) {
        diagnosisService.deleteDiagnosisById(diagnosisId);
    }

    public List<DiagnosisDto> getDiagnosisByPatientId(long patientId) {
        List<Diagnosis> diagnosisList = diagnosisService.getDiagnosisByPatientId(patientId);
        return diagnosisMapper.mapToDtoList(diagnosisList);
    }

    public List<DiagnosisDto> getDiagnosisByDoctorId(long doctorId) {
        List<Diagnosis> diagnosisList = diagnosisService.getDiagnosisByDoctorId(doctorId);
        return diagnosisMapper.mapToDtoList(diagnosisList);
    }

    public DiagnosisDto getDiagnosisByAppointmentId(long appointmentId) {
        Diagnosis diagnosis = diagnosisService.getDiagnosisByAppointmentId(appointmentId);
        return diagnosisMapper.mapToDto(diagnosis);
    }

    public DiagnosisDto updateDiagnosis(DiagnosisDto diagnosisDto) {
        Diagnosis diagnosis = diagnosisService.updateDiagnosis(diagnosisDto);
        return diagnosisMapper.mapToDto(diagnosis);
    }
}
