package com.rest.private_medical_clinic.repository;

import com.rest.private_medical_clinic.domain.DoctorAvailability;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorAvailabilityRepository extends CrudRepository<DoctorAvailability, Long> {
}
