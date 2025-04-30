package com.rest.private_medical_clinic.controller;

import com.rest.private_medical_clinic.domain.Review;
import com.rest.private_medical_clinic.domain.dto.ReviewDto;
import com.rest.private_medical_clinic.mapper.ReviewMapper;
import com.rest.private_medical_clinic.service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/reviews")
@Validated
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final ReviewMapper reviewMapper;

    @GetMapping
    public ResponseEntity<List<ReviewDto>> getAllReviews() {
        List<Review> reviews = reviewService.getAllReviews();
        return ResponseEntity.ok(reviewMapper.mapToDtoList(reviews));
    }
    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewDto> getReview(@PathVariable long reviewId) {
        Review review = reviewService.getReviewById(reviewId);
        return ResponseEntity.ok(reviewMapper.mapToDto(review));
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<ReviewDto>> getDoctorReviews(@PathVariable long doctorId) {
        List<Review> reviews = reviewService.getReviewsByDoctorId(doctorId);
        return ResponseEntity.ok(reviewMapper.mapToDtoList(reviews));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<ReviewDto>> getPatientReviews(@PathVariable long patientId) {
        List<Review> reviews = reviewService.getReviewsByPatientId(patientId);
        return ResponseEntity.ok(reviewMapper.mapToDtoList(reviews));
    }

    @PostMapping
    public ResponseEntity<ReviewDto> createReview(@Valid @RequestBody ReviewDto reviewDto) {
        Review review = reviewService.createReview(reviewDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewMapper.mapToDto(review));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable long reviewId) {
        reviewService.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }
}
