package com.rest.private_medical_clinic.scheduler;

import com.rest.private_medical_clinic.domain.Doctor;
import com.rest.private_medical_clinic.service.DoctorAvailabilityService;
import com.rest.private_medical_clinic.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class DoctorAvailabilityScheduler {

    private final DoctorService doctorService;
    private final DoctorAvailabilityService doctorAvailabilityService;

    @Scheduled(cron = "0 0 0 * * Sat")
    public void generateAvailabilityForAllDoctorsForNext7Days() {

        List<Doctor> doctors = doctorService.getAllDoctors();
        for (Doctor doctor : doctors) {
            doctorAvailabilityService.generateDoctorAvailabilityForNext7Days(doctor);
        }
    }
}
