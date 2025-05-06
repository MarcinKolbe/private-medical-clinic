package com.rest.private_medical_clinic.service;

import com.rest.private_medical_clinic.client.CalendarificClient;
import com.rest.private_medical_clinic.domain.dto.CalendarificResponseDto;
import com.rest.private_medical_clinic.mapper.HolidayMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class HolidayService {

    private final CalendarificClient calendarificClient;
    private final HolidayMapper holidayMapper;

    public List<LocalDate> getHolidaysForYear(int year, String country) {
        CalendarificResponseDto dto = calendarificClient.fetchHolidays(year, country);
        return holidayMapper.mapToLocalDate(dto);
    }
}
