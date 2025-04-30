package com.rest.private_medical_clinic.repository;

import com.rest.private_medical_clinic.domain.Appointment;
import com.rest.private_medical_clinic.enums.AppointmentStatus;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AppointmentRepository extends CrudRepository<Appointment, Long> {

    @Override
    List<Appointment> findAll();

    List<Appointment> findAllByDoctorIdOrderByDateDesc (long doctorId);

    List<Appointment> findAllByPatientIdOrderByDateDesc (long patientId);

    List<Appointment> findAllByDateOrderByDateDesc (LocalDate date);

    List<Appointment> findAllByStatus (AppointmentStatus status);

}
