package com.rest.private_medical_clinic.repository;

import com.rest.private_medical_clinic.domain.DoctorAvailability;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface DoctorAvailabilityRepository extends CrudRepository<DoctorAvailability, Long> {

    @Override
    List<DoctorAvailability> findAll();

    List<DoctorAvailability> findByDoctorId(Long id);

    List<DoctorAvailability> findAllByDoctorIdAndAvailableTrueOrderByDateAscStartTimeAsc (Long doctorId);

    Optional<DoctorAvailability> findByDoctorIdAndDateAndStartTime(long doctorId, LocalDate date, LocalTime startTime);
}
