package com.rest.private_medical_clinic.service;

import com.rest.private_medical_clinic.domain.Appointment;
import com.rest.private_medical_clinic.domain.Doctor;
import com.rest.private_medical_clinic.domain.Patient;
import com.rest.private_medical_clinic.domain.Review;
import com.rest.private_medical_clinic.domain.dto.ReviewDto;
import com.rest.private_medical_clinic.exception.AppointmentNotFoundException;
import com.rest.private_medical_clinic.exception.DoctorNotFoundException;
import com.rest.private_medical_clinic.exception.PatientNotFoundException;
import com.rest.private_medical_clinic.exception.ReviewNotFoundException;
import com.rest.private_medical_clinic.repository.AppointmentRepository;
import com.rest.private_medical_clinic.repository.DoctorRepository;
import com.rest.private_medical_clinic.repository.PatientRepository;
import com.rest.private_medical_clinic.repository.ReviewRepository;
import com.rest.private_medical_clinic.validator.ReviewValidator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final AppointmentRepository appointmentRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final ReviewValidator reviewValidator;

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Review getReviewById(long reviewId) {
        return reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException(reviewId));
    }

    public List<Review> getReviewsByDoctorId(long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(
                () -> new DoctorNotFoundException(doctorId));
        return reviewRepository.findAllByDoctor_Id(doctor.getId());
    }

    public List<Review> getReviewsByPatientId(long patientId) {
        Patient patient = patientRepository.findById(patientId).orElseThrow(
                () -> new PatientNotFoundException(patientId));
        return reviewRepository.findAllByPatient_Id(patient.getId());
    }

    @Transactional
    public Review createReview(ReviewDto reviewRequest) {

        reviewValidator.validateReview(reviewRequest);

        Appointment appointment = appointmentRepository.findById(reviewRequest.getAppointmentId()).orElseThrow(
                () -> new AppointmentNotFoundException(reviewRequest.getAppointmentId()));

        Review review = new Review();
        review.setAppointment(appointment);
        review.setDoctor(appointment.getDoctor());
        review.setPatient(appointment.getPatient());
        review.setRating(reviewRequest.getRating());
        review.setComment(reviewRequest.getComment());
        review.setCreatedAt(LocalDateTime.now());
        reviewRepository.save(review);
        return review;
    }

    @Transactional
    public void deleteReview(long reviewId) {
        Review review = getReviewById(reviewId);
        reviewRepository.delete(review);
    }
}