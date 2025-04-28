package com.rest.private_medical_clinic.service;

import com.rest.private_medical_clinic.domain.Doctor;
import com.rest.private_medical_clinic.domain.DoctorAvailability;
import com.rest.private_medical_clinic.domain.DoctorScheduleTemplate;
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

    private final DoctorRepository doctorRepo;
    private final DoctorAvailabilityRepository availabilityRepo;
    private final DoctorScheduleTemplateRepository scheduleTemplateRepo;

    public List<DoctorAvailability> findAllAvailabilityForAllDoctors() {
        return availabilityRepo.findAll();
    }

    public DoctorAvailability findDoctorAvailabilityById(Long id) {
        return availabilityRepo.findById(id).orElseThrow(() -> new RuntimeException("No Doctor Availability found with id: " + id));
    }

    public List<DoctorAvailability> findDoctorAvailabilityByDoctorId(Long doctorId) {
        Doctor doctor = doctorRepo.findById(doctorId).orElseThrow(() -> new RuntimeException("No Doctor found with id: " + doctorId));
        return availabilityRepo.findByDoctorId(doctor.getId());
    }

    public DoctorAvailability saveDoctorAvailability(DoctorAvailability doctorAvailability) {
        return availabilityRepo.save(doctorAvailability);
    }

    public DoctorAvailability updateDoctorAvailability(DoctorAvailability doctorAvailability) {
        DoctorAvailability availability = findDoctorAvailabilityById(doctorAvailability.getId());
        availability.setDate(doctorAvailability.getDate());
        availability.setStartTime(doctorAvailability.getStartTime());
        availability.setEndTime(doctorAvailability.getEndTime());
        availability.setAvailable(doctorAvailability.isAvailable());
        return availabilityRepo.save(availability);
    }

    public void deleteDoctorAvailability(Long id) {
        DoctorAvailability doctorAvailability = availabilityRepo.findById(id).orElseThrow(() -> new RuntimeException("No Doctor Availability found with id: " + id));
        availabilityRepo.delete(doctorAvailability);
    }

    @Transactional
    public void generateDoctorAvailabilityForNext7Days(Doctor doctor) {
        List<DoctorScheduleTemplate> templates = scheduleTemplateRepo.findByDoctor(doctor);

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
