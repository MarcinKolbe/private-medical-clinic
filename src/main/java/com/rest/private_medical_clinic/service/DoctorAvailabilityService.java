package com.rest.private_medical_clinic.service;

import com.rest.private_medical_clinic.domain.Doctor;
import com.rest.private_medical_clinic.domain.DoctorAvailability;
import com.rest.private_medical_clinic.domain.DoctorScheduleTemplate;
import com.rest.private_medical_clinic.domain.dto.DoctorAvailabilityDto;
import com.rest.private_medical_clinic.exception.DoctorAvailabilityException;
import com.rest.private_medical_clinic.exception.DoctorNotFoundException;
import com.rest.private_medical_clinic.exception.DoctorScheduleTemplateException;
import com.rest.private_medical_clinic.observer.DoctorScheduleTemplateChangedEvent;
import com.rest.private_medical_clinic.repository.DoctorAvailabilityRepository;
import com.rest.private_medical_clinic.repository.DoctorRepository;
import com.rest.private_medical_clinic.repository.DoctorScheduleTemplateRepository;
import com.rest.private_medical_clinic.strategy.AvailabilityStrategy;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DoctorAvailabilityService {

    private final Map<String, AvailabilityStrategy> strategies;
    private final DoctorRepository doctorRepository;
    private final DoctorAvailabilityRepository availabilityRepo;
    private final DoctorScheduleTemplateRepository scheduleTemplateRepo;
    private final HolidayService holidayService;

    public List<DoctorAvailability> getAllAvailabilityForAllDoctors() {
        return availabilityRepo.findAll();
    }

    public DoctorAvailability getDoctorAvailabilityById(Long id) {
        return availabilityRepo.findById(id).orElseThrow(() -> new DoctorAvailabilityException(id));
    }

    public List<DoctorAvailability> getDoctorAvailabilityByDoctorId(Long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new DoctorNotFoundException(doctorId));
        return availabilityRepo.findAllByDoctorId(doctor.getId());
    }

    @Transactional
    public void addDoctorAvailability(long doctorId) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(
                () -> new DoctorNotFoundException(doctorId));
        generateDoctorAvailabilityForNext7Days(doctor);
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

    public List<DoctorAvailability> getAvailableSlotsForDoctor(long doctorId) {
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

        List<LocalDate> holidays = holidayService.getHolidaysForYear(today.getYear(), "PL");

        for (LocalDate date = today; !date.isAfter(endDate); date = date.plusDays(1)) {

            if (holidays.contains(date)) continue;

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

    @EventListener
    @Transactional
    public void onTemplateChange(DoctorScheduleTemplateChangedEvent event) {
        generateDoctorAvailabilityForSpecificTemplateForNext7Days(event.getTemplateId());
    }

    public void generateDoctorAvailabilityForSpecificTemplateForNext7Days(long templateId) {
        DoctorScheduleTemplate template = scheduleTemplateRepo.findById(templateId).orElseThrow(
                () -> new DoctorScheduleTemplateException(templateId));
        Doctor doctor = doctorRepository.findById(template.getDoctor().getId()).orElseThrow(
                () -> new DoctorNotFoundException(template.getDoctor().getId()));

        LocalDate today = LocalDate.now();
        LocalDate endDate = today.plusDays(7);

        List<LocalDate> holidays = holidayService.getHolidaysForYear(today.getYear(), "PL");

        for (LocalDate date = today; !date.isAfter(endDate); date = date.plusDays(1)) {

            if (holidays.contains(date)) continue;

            DayOfWeek dayOfWeek = date.getDayOfWeek();

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

    public boolean checkSlot(long doctorId, LocalDate date, LocalTime start, LocalTime end, String strategyKey) {
        Doctor doctor = doctorRepository.findById(doctorId).orElseThrow(() -> new DoctorNotFoundException(doctorId));
        AvailabilityStrategy strategy = strategies.getOrDefault(strategyKey, strategies.get("defaultStrategy"));
        return strategy.isSlotAvailable(doctor, date, start, end);
    }
}