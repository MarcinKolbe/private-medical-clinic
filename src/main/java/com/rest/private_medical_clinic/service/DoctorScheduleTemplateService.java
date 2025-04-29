package com.rest.private_medical_clinic.service;

import com.rest.private_medical_clinic.domain.Doctor;
import com.rest.private_medical_clinic.domain.DoctorScheduleTemplate;
import com.rest.private_medical_clinic.domain.dto.DoctorScheduleTemplateDto;
import com.rest.private_medical_clinic.exeption.DoctorScheduleTemplateException;
import com.rest.private_medical_clinic.repository.DoctorScheduleTemplateRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorScheduleTemplateService {

    private final DoctorScheduleTemplateRepository repository;
    private final DoctorService doctorService;

    public List<DoctorScheduleTemplate> getAllDoctorScheduleTemplate() {
        return repository.findAll();
    }

    public DoctorScheduleTemplate getDoctorScheduleTemplateById(Long id) {
        return repository.findById(id).orElseThrow(() -> new DoctorScheduleTemplateException(id));
    }

    @Transactional
    public DoctorScheduleTemplate createDoctorScheduleTemplate(long doctorId, DoctorScheduleTemplateDto doctorScheduleTemplateRequest) {
        Doctor doctor = doctorService.getDoctor(doctorId);

        if (doctorScheduleTemplateRequest.getStartTime().isAfter(doctorScheduleTemplateRequest.getEndTime())) {
            throw new IllegalArgumentException("Start time must be before end time");
        }
        DoctorScheduleTemplate doctorScheduleTemplate = new DoctorScheduleTemplate();
        doctorScheduleTemplate.setDoctor(doctor);
        doctorScheduleTemplate.setDayOfWeek(doctorScheduleTemplateRequest.getDayOfWeek());
        doctorScheduleTemplate.setStartTime(doctorScheduleTemplateRequest.getStartTime());
        doctorScheduleTemplate.setEndTime(doctorScheduleTemplateRequest.getEndTime());
        repository.save(doctorScheduleTemplate);
        return doctorScheduleTemplate;
    }

    @Transactional
    public void deleteDoctorScheduleTemplateById(Long id) {
        repository.deleteById(id);
    }

    public List<DoctorScheduleTemplate> getDoctorScheduleTemplateByDoctorId(Long id) {
        Doctor doctor = doctorService.getDoctor(id);
        return repository.findByDoctor_Id(doctor.getId());
    }

    @Transactional
    public DoctorScheduleTemplate updateDoctorScheduleTemplate(long doctorId, DoctorScheduleTemplateDto doctorScheduleTemplateRequest) {
        doctorService.getDoctor(doctorId);

        if (doctorScheduleTemplateRequest.getStartTime().isAfter(doctorScheduleTemplateRequest.getEndTime())) {
            throw new IllegalArgumentException("Start time must be before end time");
        }

        DoctorScheduleTemplate doctorScheduleTemplate = getDoctorScheduleTemplateById(doctorScheduleTemplateRequest.getId());

        doctorScheduleTemplate.setDayOfWeek(doctorScheduleTemplateRequest.getDayOfWeek());
        doctorScheduleTemplate.setStartTime(doctorScheduleTemplateRequest.getStartTime());
        doctorScheduleTemplate.setEndTime(doctorScheduleTemplateRequest.getEndTime());
        repository.save(doctorScheduleTemplate);
        return doctorScheduleTemplate;
    }
}