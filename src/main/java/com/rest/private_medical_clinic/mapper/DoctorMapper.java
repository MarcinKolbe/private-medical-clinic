package com.rest.private_medical_clinic.mapper;

import com.rest.private_medical_clinic.domain.Appointment;
import com.rest.private_medical_clinic.domain.Doctor;
import com.rest.private_medical_clinic.domain.Review;
import com.rest.private_medical_clinic.domain.User;
import com.rest.private_medical_clinic.domain.dto.DoctorDto;
import com.rest.private_medical_clinic.exception.AppointmentNotFoundException;
import com.rest.private_medical_clinic.exception.ReviewNotFoundException;
import com.rest.private_medical_clinic.exception.UserNotFoundException;
import com.rest.private_medical_clinic.repository.AppointmentRepository;
import com.rest.private_medical_clinic.repository.ReviewRepository;
import com.rest.private_medical_clinic.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DoctorMapper {

    private AppointmentRepository appointmentRepository;
    private ReviewRepository reviewRepository;
    private UserRepository userRepository;

    public DoctorDto mapToDto (Doctor doctor) {
        return new DoctorDto(doctor.getId(), doctor.getFirstname(), doctor.getLastname(),
                doctor.getSpecialization(), doctor.getRating(), doctor.getUser().getId(),
                doctor.getAppointmentList().stream().map(Appointment::getId).toList(),
                doctor.getReviewList().stream().map(Review::getId).toList());
    }

    public Doctor mapToEntity (DoctorDto doctorDto) {
         List<Appointment> appointments = doctorDto.getAppointmentIdList().stream()
                 .map(id -> appointmentRepository.findById(id).orElseThrow(() -> new AppointmentNotFoundException(id)))
                 .toList();
         List<Review> reviews = doctorDto.getReviewIdList().stream()
                 .map(id -> reviewRepository.findById(id).orElseThrow(() -> new ReviewNotFoundException(id)))
                 .toList();
         User user = userRepository.findById(doctorDto.getUserId()).orElseThrow(() -> new UserNotFoundException(doctorDto.getId()));

         Doctor doctor = new Doctor();
         doctor.setId(doctorDto.getId());
         doctor.setFirstname(doctorDto.getFirstname());
         doctor.setLastname(doctorDto.getLastname());
         doctor.setSpecialization(doctorDto.getSpecialization());
         doctor.setRating(doctorDto.getRating());
         doctor.setUser(user);
         doctor.setAppointmentList(appointments);
         doctor.setReviewList(reviews);
         return doctor;
    }

    public List<DoctorDto> mapToDtoList (List<Doctor> doctors) {
        return doctors.stream()
                .map(this::mapToDto)
                .toList();
    }

    public List<Doctor> mapToEntityList (List<DoctorDto> doctorDtos) {
        return doctorDtos.stream()
                .map(this::mapToEntity)
                .toList();
    }
}
