package com.rest.private_medical_clinic.repository;

import com.rest.private_medical_clinic.domain.Diagnosis;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiagnosisRepository extends CrudRepository<Diagnosis, Long> {

    @Override
    List<Diagnosis> findAll();

    List<Diagnosis> findByAppointment_PatientId(Long id);

    List<Diagnosis> findByAppointment_DoctorId(Long id);

    Diagnosis findByAppointment_Id(long appointmentId);
}
