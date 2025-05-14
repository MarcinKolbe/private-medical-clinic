package com.rest.private_medical_clinic.strategy;

import com.rest.private_medical_clinic.domain.Doctor;
import com.rest.private_medical_clinic.domain.DoctorAvailability;
import com.rest.private_medical_clinic.repository.DoctorAvailabilityRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Component("defaultStrategy")
@RequiredArgsConstructor
public class DefaultAvailabilityStrategy implements AvailabilityStrategy {

    private final DoctorAvailabilityRepository doctorAvailabilityRepository;

    @Override
    public boolean isSlotAvailable(Doctor doctor, LocalDate date, LocalTime start, LocalTime end) {
        List<DoctorAvailability> slots = doctorAvailabilityRepository
                .findByDoctorAndDateAndAvailableTrue(doctor, date);
        return slots.stream().anyMatch(s ->
                !s.getStartTime().isAfter(start) &&
                        !s.getEndTime().isBefore(end)
        );
    }
}
