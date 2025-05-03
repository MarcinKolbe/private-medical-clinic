package com.rest.private_medical_clinic.mapper;

import com.rest.private_medical_clinic.domain.Appointment;
import com.rest.private_medical_clinic.domain.Patient;
import com.rest.private_medical_clinic.domain.Review;
import com.rest.private_medical_clinic.domain.User;
import com.rest.private_medical_clinic.domain.dto.PatientDto;
import com.rest.private_medical_clinic.service.AppointmentService;
import com.rest.private_medical_clinic.service.ReviewService;
import com.rest.private_medical_clinic.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PatientMapper {

    private final AppointmentService appointmentService;
    private final ReviewService reviewService;
    private final UserService userService;

    public PatientDto mapToDto(Patient patient) {
        return new PatientDto(patient.getId(), patient.getFirstName(), patient.getLastName(),
                patient.getPhoneNumber(), patient.getPesel(), patient.getBirthDate(), patient.getUser().getId(),
                patient.getAppointmentList().stream().map(Appointment::getId).toList(),
                patient.getReviewList().stream().map(Review::getId).toList());
    }

    public Patient mapToEntity(PatientDto patientDto) {
        List<Appointment> appointments = patientDto.getAppointmentIdList().stream()
                .map(appointmentService::getAppointmentById)
                .toList();

        List<Review> reviews = patientDto.getReviewIdList().stream()
                .map(reviewService::getReviewById)
                .toList();

        User user = userService.getUserById(patientDto.getUserId());

        Patient patient = new Patient();
        patient.setId(patientDto.getId());
        patient.setFirstName(patientDto.getFirstName());
        patient.setLastName(patientDto.getLastName());
        patient.setPhoneNumber(patientDto.getPhoneNumber());
        patient.setPesel(patientDto.getPesel());
        patient.setBirthDate(patientDto.getBirthDate());
        patient.setUser(user);
        patient.setAppointmentList(appointments);
        patient.setReviewList(reviews);
        return patient;
    }

    public List<PatientDto> mapToDtoList(List<Patient> patients) {
        return patients.stream()
                .map(this::mapToDto)
                .toList();
    }

    public List<Patient> mapToEntityList(List<PatientDto> patientDtos) {
        return patientDtos.stream()
                .map(this::mapToEntity)
                .toList();
    }
}
