package com.rest.private_medical_clinic.repository;

import com.rest.private_medical_clinic.domain.Patient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientRepository extends CrudRepository<Patient, Long> {

    @Override
    List<Patient> findAll();
}
