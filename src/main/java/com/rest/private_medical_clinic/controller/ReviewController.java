package com.rest.private_medical_clinic.controller;

import com.rest.private_medical_clinic.domain.dto.ReviewDto;
import com.rest.private_medical_clinic.facade.ReviewFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/reviews")
@Validated
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewFacade reviewFacade;
    private final Logger LOGGER = LoggerFactory.getLogger(ReviewController.class);

    @GetMapping
    public ResponseEntity<List<ReviewDto>> getAllReviews() {
        return ResponseEntity.ok(reviewFacade.getAllReviews());
    }

    @GetMapping("/{reviewId}")
    public ResponseEntity<ReviewDto> getReview(@PathVariable long reviewId) {
        return ResponseEntity.ok(reviewFacade.getReviewById(reviewId));
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<ReviewDto>> getDoctorReviews(@PathVariable long doctorId) {
        return ResponseEntity.ok(reviewFacade.getReviewsByDoctorId(doctorId));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<ReviewDto>> getPatientReviews(@PathVariable long patientId) {
        return ResponseEntity.ok(reviewFacade.getReviewsByPatientId(patientId));
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReviewDto> createReview(@Valid @RequestBody ReviewDto reviewDto) {
        LOGGER.info("Incoming create request ReviewDto: {}", reviewDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewFacade.createReview(reviewDto));
    }

    @DeleteMapping("/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable long reviewId) {
        reviewFacade.deleteReview(reviewId);
        return ResponseEntity.noContent().build();
    }
}
