package com.rest.private_medical_clinic.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {

    private long reviewId;
    private long appointmentId;
    private int rating;
    private String comment;
    private LocalDateTime createdAt;
}
