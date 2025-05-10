package com.rest.private_medical_clinic.facade;

import com.rest.private_medical_clinic.domain.Review;
import com.rest.private_medical_clinic.domain.dto.ReviewDto;
import com.rest.private_medical_clinic.mapper.ReviewMapper;
import com.rest.private_medical_clinic.service.ReviewService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewFacade {

    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;

    public List<ReviewDto> getAllReviews() {
        List<Review> reviews = reviewService.getAllReviews();
        return reviewMapper.mapToDtoList(reviews);
    }

    public ReviewDto getReviewById(long reviewId) {
        Review review = reviewService.getReviewById(reviewId);
        return reviewMapper.mapToDto(review);
    }

    public List<ReviewDto> getReviewsByDoctorId(long doctorId) {
        List<Review> reviews = reviewService.getReviewsByDoctorId(doctorId);
        return reviewMapper.mapToDtoList(reviews);
    }

    public List<ReviewDto> getReviewsByPatientId(long patientId) {
        List<Review> reviews = reviewService.getReviewsByPatientId(patientId);
        return reviewMapper.mapToDtoList(reviews);
    }

    public ReviewDto createReview(ReviewDto reviewDto) {
        Review review = reviewService.createReview(reviewDto);
        return reviewMapper.mapToDto(review);
    }

    public void deleteReview(long reviewId) {
        reviewService.deleteReview(reviewId);
    }
}
