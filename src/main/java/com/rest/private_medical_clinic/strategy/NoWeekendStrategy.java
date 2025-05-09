package com.rest.private_medical_clinic.strategy;

import com.rest.private_medical_clinic.domain.Doctor;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;

@Component("NoWeekendStrategy")
public class NoWeekendStrategy implements AvailabilityStrategy{
    @Override
    public boolean isSlotAvailable(Doctor doctor, LocalDate date, LocalTime start, LocalTime end) {
        DayOfWeek dayOfWeek = date.getDayOfWeek();
        return !(dayOfWeek == DayOfWeek.SATURDAY || dayOfWeek == DayOfWeek.SUNDAY);
    }
}
