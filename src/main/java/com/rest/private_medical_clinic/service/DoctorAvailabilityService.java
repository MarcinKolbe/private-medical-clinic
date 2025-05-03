package com.rest.private_medical_clinic.service;

import com.rest.private_medical_clinic.domain.Doctor;
import com.rest.private_medical_clinic.domain.DoctorAvailability;
import com.rest.private_medical_clinic.domain.DoctorScheduleTemplate;
import com.rest.private_medical_clinic.domain.dto.DoctorAvailabilityDto;
import com.rest.private_medical_clinic.exception.DoctorAvailabilityException;
import com.rest.private_medical_clinic.exception.DoctorNotFoundException;
import com.rest.private_medical_clinic.repository.DoctorAvailabilityRepository;
import com.rest.private_medical_clinic.repository.DoctorRepository;
import com.rest.private_medical_clinic.repository.DoctorScheduleTemplateRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorAvailabilityService {

    private final DoctorService doctorService;
    private final DoctorAvailabilityRepository availabilityRepo;
    private final DoctorScheduleTemplateRepository scheduleTemplateRepo;

    public List<DoctorAvailability> getAllAvailabilityForAllDoctors() {
        return availabilityRepo.findAll();
    }

    public DoctorAvailability getDoctorAvailabilityById(Long id) {
        return availabilityRepo.findById(id).orElseThrow(() -> new DoctorAvailabilityException(id));
    }

    public List<DoctorAvailability> getDoctorAvailabilityByDoctorId(Long doctorId) {
        Doctor doctor = doctorService.getDoctor(doctorId);
        return availabilityRepo.findByDoctorId(doctor.getId());
    }

    @Transactional
    public DoctorAvailability addDoctorAvailability(DoctorAvailabilityDto doctorAvailabilityRequest) {
        Doctor doctor = doctorService.getDoctor(doctorAvailabilityRequest.getDoctorId());
        DoctorAvailability availability = new DoctorAvailability();
        availability.setDoctor(doctor);
        availability.setDate(doctorAvailabilityRequest.getDate());
        availability.setStartTime(doctorAvailabilityRequest.getStartTime());
        availability.setEndTime(doctorAvailabilityRequest.getEndTime());
        availability.setAvailable(true);
        availabilityRepo.save(availability);
        return availability;
    }

    @Transactional
    public DoctorAvailability updateDoctorAvailability(DoctorAvailabilityDto doctorAvailability) {
        DoctorAvailability availability = getDoctorAvailabilityById(doctorAvailability.getId());
        availability.setDate(doctorAvailability.getDate());
        availability.setStartTime(doctorAvailability.getStartTime());
        availability.setEndTime(doctorAvailability.getEndTime());
        availability.setAvailable(doctorAvailability.isAvailable());
        return availabilityRepo.save(availability);
    }

    @Transactional
    public void deleteDoctorAvailability(Long id) {
        DoctorAvailability doctorAvailability = availabilityRepo.findById(id).orElseThrow(() -> new RuntimeException("No Doctor Availability found with id: " + id));
        availabilityRepo.delete(doctorAvailability);
    }

    public List<DoctorAvailability> getAvailableSlotsForDoctor(Long doctorId) {
        return availabilityRepo.findAllByDoctorIdAndAvailableTrueOrderByDateAscStartTimeAsc(doctorId);
    }

    @Transactional
    public void markSlotAsUnavailable(Long doctorId, LocalDate date, LocalTime startTime) {
        DoctorAvailability availability = availabilityRepo
                .findByDoctorIdAndDateAndStartTime(doctorId, date, startTime)
                .orElseThrow(() -> new IllegalStateException("Availability slot not found."));

        availability.setAvailable(false);
        availabilityRepo.save(availability);
    }

    @Transactional
    public void markSlotAsAvailable(Long doctorId, LocalDate date, LocalTime time) {
        DoctorAvailability availability = availabilityRepo
                .findByDoctorIdAndDateAndStartTime(doctorId, date, time)
                .orElseThrow(() -> new IllegalStateException("Availability slot not found."));

        availability.setAvailable(true);
        availabilityRepo.save(availability);
    }

    @Transactional
    public void generateDoctorAvailabilityForNext7Days(Doctor doctor) {
        List<DoctorScheduleTemplate> templates = scheduleTemplateRepo.findByDoctor_Id(doctor.getId());

        LocalDate today = LocalDate.now();
        LocalDate endDate = today.plusDays(7);

        for (LocalDate date = today; !date.isAfter(endDate); date = date.plusDays(1)) {
            DayOfWeek dayOfWeek = date.getDayOfWeek();

            for (DoctorScheduleTemplate template : templates) {
                if (template.getDayOfWeek() == dayOfWeek) {
                    LocalTime currentTime = template.getStartTime();
                    while (currentTime.plusMinutes(30).isBefore(template.getEndTime())
                            || currentTime.plusMinutes(30).equals(template.getEndTime())) {
                        DoctorAvailability availability = new DoctorAvailability();
                        availability.setDoctor(doctor);
                        availability.setDate(date);
                        availability.setStartTime(currentTime);
                        availability.setEndTime(currentTime.plusMinutes(30));
                        availability.setAvailable(true);
                        availabilityRepo.save(availability);
                        currentTime = currentTime.plusMinutes(30);
                    }
                }
            }
        }
    }
}