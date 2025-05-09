package com.rest.private_medical_clinic.mapper;

import com.rest.private_medical_clinic.domain.Appointment;
import com.rest.private_medical_clinic.domain.Diagnosis;
import com.rest.private_medical_clinic.domain.Doctor;
import com.rest.private_medical_clinic.domain.Patient;
import com.rest.private_medical_clinic.domain.dto.AppointmentDto;
import com.rest.private_medical_clinic.exception.DiagnosisNotFoundException;
import com.rest.private_medical_clinic.exception.DoctorNotFoundException;
import com.rest.private_medical_clinic.exception.PatientNotFoundException;
import com.rest.private_medical_clinic.repository.DiagnosisRepository;
import com.rest.private_medical_clinic.repository.DoctorRepository;
import com.rest.private_medical_clinic.repository.PatientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentMapper {

    private final PatientRepository patientRepo;
    private final DoctorRepository doctorRepo;
    private final DiagnosisRepository diagnosisRepo;

    public AppointmentDto mapToDto (Appointment appointment) {
        long diagnosisId = 0;

        if (appointment.getDiagnosis() != null) {
            diagnosisId = appointment.getDiagnosis().getId();
        }

        return new AppointmentDto(appointment.getId(), appointment.getDoctor().getId(), appointment.getPatient().getId(),
                appointment.getDate(), appointment.getTime(), appointment.getStatus(), appointment.getNotes(),
                diagnosisId);
    }

    public Appointment mapToEntity (AppointmentDto appointmentDto) {
        Doctor doctor = doctorRepo.findById(appointmentDto.getDoctorId()).orElseThrow(
                () -> new DoctorNotFoundException(appointmentDto.getDoctorId()));
        Patient patient = patientRepo.findById(appointmentDto.getPatientId()).orElseThrow(
                () -> new PatientNotFoundException(appointmentDto.getPatientId()));


        Appointment appointment =  new Appointment();
        appointment.setId(appointmentDto.getId());
        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setDate(appointmentDto.getDate());
        appointment.setTime(appointmentDto.getTime());
        appointment.setStatus(appointmentDto.getStatus());
        appointment.setNotes(appointmentDto.getNotes());

        if (appointmentDto.getDiagnoseId() != 0) {
            Diagnosis diagnosis = diagnosisRepo.findById(appointmentDto.getDiagnoseId()).orElseThrow(
                    () -> new DiagnosisNotFoundException(appointmentDto.getDiagnoseId()));
            appointment.setDiagnosis(diagnosis);
        }

        return appointment;
    }

    public List<AppointmentDto> mapToDtoList (List<Appointment> appointments) {
        return appointments.stream()
                .map(this::mapToDto)
                .toList();
    }

    public List<Appointment> mapToEntityList (List<AppointmentDto> appointmentDtos) {
        return appointmentDtos.stream()
                .map(this::mapToEntity)
                .toList();
    }
}
