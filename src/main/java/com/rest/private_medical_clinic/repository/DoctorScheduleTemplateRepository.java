package com.rest.private_medical_clinic.repository;

import com.rest.private_medical_clinic.domain.Doctor;
import com.rest.private_medical_clinic.domain.DoctorScheduleTemplate;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DoctorScheduleTemplateRepository extends CrudRepository<DoctorScheduleTemplate, Long> {

    @Override
    List<DoctorScheduleTemplate> findAll();

    List<DoctorScheduleTemplate> findByDoctor_Id(long doctorId);
}
