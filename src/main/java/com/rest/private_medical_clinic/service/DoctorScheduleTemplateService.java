package com.rest.private_medical_clinic.service;

import com.rest.private_medical_clinic.domain.Doctor;
import com.rest.private_medical_clinic.domain.DoctorScheduleTemplate;
import com.rest.private_medical_clinic.domain.dto.DoctorScheduleTemplateDto;
import com.rest.private_medical_clinic.exception.DoctorScheduleTemplateException;
import com.rest.private_medical_clinic.repository.DoctorRepository;
import com.rest.private_medical_clinic.repository.DoctorScheduleTemplateRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorScheduleTemplateService {

    private final DoctorScheduleTemplateRepository repository;
    private final DoctorRepository doctorRepository;
    private final DoctorAvailabilityService availabilityService;
    private final DoctorAvailabilityService doctorAvailabilityService;

    public List<DoctorScheduleTemplate> getAllDoctorScheduleTemplate() {
        return repository.findAll();
    }

    public DoctorScheduleTemplate getDoctorScheduleTemplateById(Long id) {
        return repository.findById(id).orElseThrow(() -> new DoctorScheduleTemplateException(id));
    }

    @Transactional
    public DoctorScheduleTemplate createDoctorScheduleTemplate(long doctorId, DoctorScheduleTemplateDto doctorScheduleTemplateRequest) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(
                () -> new DoctorScheduleTemplateException(doctorId));

        if (doctorScheduleTemplateRequest.getStartTime().isAfter(doctorScheduleTemplateRequest.getEndTime())) {
            throw new IllegalArgumentException("Start time must be before end time");
        }
        DoctorScheduleTemplate doctorScheduleTemplate = new DoctorScheduleTemplate();
        doctorScheduleTemplate.setDoctor(doctor);
        doctorScheduleTemplate.setDayOfWeek(doctorScheduleTemplateRequest.getDayOfWeek());
        doctorScheduleTemplate.setStartTime(doctorScheduleTemplateRequest.getStartTime());
        doctorScheduleTemplate.setEndTime(doctorScheduleTemplateRequest.getEndTime());
        repository.save(doctorScheduleTemplate);
        doctorAvailabilityService.generateDoctorAvailabilityForSpecificTemplateForNext7Days(doctorScheduleTemplate.getId());
        return doctorScheduleTemplate;
    }

    @Transactional
    public void deleteDoctorScheduleTemplateById(Long id) {
        repository.deleteById(id);
    }

    public List<DoctorScheduleTemplate> getDoctorScheduleTemplateByDoctorId(Long id) {
        Doctor doctor = doctorRepository.findById(id).orElseThrow(() -> new DoctorScheduleTemplateException(id));
        return repository.findByDoctor_Id(doctor.getId());
    }

    @Transactional
    public DoctorScheduleTemplate updateDoctorScheduleTemplate(long templateId, DoctorScheduleTemplateDto doctorScheduleTemplateRequest) {

        DoctorScheduleTemplate doctorScheduleTemplate = getDoctorScheduleTemplateById(templateId);

        LocalTime newStart = doctorScheduleTemplateRequest.getStartTime() != null ? doctorScheduleTemplateRequest.getStartTime() : doctorScheduleTemplate.getStartTime();
        LocalTime newEnd   = doctorScheduleTemplateRequest.getEndTime()   != null ? doctorScheduleTemplateRequest.getEndTime()   : doctorScheduleTemplate.getEndTime();

        if (newStart.isAfter(newEnd) || newStart.equals(newEnd)) {
            throw new IllegalArgumentException("Start time must be before end time");
        }

        if (doctorScheduleTemplateRequest.getDayOfWeek() != null) {
            doctorScheduleTemplate.setDayOfWeek(doctorScheduleTemplateRequest.getDayOfWeek());
        }
        if (doctorScheduleTemplateRequest.getStartTime() != null) {
            doctorScheduleTemplate.setStartTime(doctorScheduleTemplateRequest.getStartTime());
        }
        if (doctorScheduleTemplateRequest.getEndTime() != null) {
            doctorScheduleTemplate.setEndTime(doctorScheduleTemplateRequest.getEndTime());
        }

        repository.save(doctorScheduleTemplate);

        return doctorScheduleTemplate;
    }
}