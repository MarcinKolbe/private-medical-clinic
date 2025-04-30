package com.rest.private_medical_clinic.mapper;

import com.rest.private_medical_clinic.domain.Review;
import com.rest.private_medical_clinic.domain.dto.ReviewDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReviewMapper {

    public ReviewDto mapToDto(Review review) {
        return new ReviewDto(review.getId(), review.getAppointment().getId(),
                review.getRating(), review.getComment(), review.getCreatedAt());
    }

    public List<ReviewDto> mapToDtoList(List<Review> reviews) {
        return reviews.stream()
                .map(this::mapToDto)
                .toList();
    }
}
