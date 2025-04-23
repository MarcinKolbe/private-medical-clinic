package com.rest.private_medical_clinic.repository;

import com.rest.private_medical_clinic.domain.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {
}
