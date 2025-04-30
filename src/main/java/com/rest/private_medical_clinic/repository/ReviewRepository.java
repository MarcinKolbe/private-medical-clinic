package com.rest.private_medical_clinic.repository;

import com.rest.private_medical_clinic.domain.Review;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends CrudRepository<Review, Long> {

    @Override
    List<Review> findAll();

    List<Review> findAllByDoctor_Id(Long id);

    List<Review> findAllByPatient_Id(Long id);
}
