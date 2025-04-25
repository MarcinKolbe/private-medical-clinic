package com.rest.private_medical_clinic.service;

import com.rest.private_medical_clinic.domain.Doctor;
import com.rest.private_medical_clinic.domain.DoctorScheduleTemplate;
import com.rest.private_medical_clinic.repository.DoctorRepository;
import com.rest.private_medical_clinic.repository.DoctorScheduleTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorScheduleTemplateService {

    private final DoctorScheduleTemplateRepository repository;
    private final DoctorRepository doctorRepo;

    public List<DoctorScheduleTemplate> getAllDoctorScheduleTemplate() {
        return repository.findAll();
    }

    public DoctorScheduleTemplate getDoctorScheduleTemplateById(Long id) {
        return repository.findById(id).orElseThrow(() -> new RuntimeException("Template not found"));
    }

    public DoctorScheduleTemplate createDoctorScheduleTemplate(DoctorScheduleTemplate doctorScheduleTemplate) {
        return repository.save(doctorScheduleTemplate);
    }

    public void deleteDoctorScheduleTemplateById(Long id) {
        repository.deleteById(id);
    }

    public DoctorScheduleTemplate updateDoctorScheduleTemplate(DoctorScheduleTemplate doctorScheduleTemplate) {
        return repository.save(doctorScheduleTemplate);
    }

    public List<DoctorScheduleTemplate> getDoctorScheduleTemplateByDoctorId(Long id) {
        Doctor doctor = doctorRepo.findById(id).orElseThrow(() -> new RuntimeException("Doctor not found"));
        return repository.findByDoctor(doctor);
    }


}
