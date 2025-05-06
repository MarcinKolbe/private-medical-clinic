package com.rest.private_medical_clinic.controller;

import com.rest.private_medical_clinic.service.HolidayService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/v1/holidays")
@RequiredArgsConstructor
public class HolidayController {

    private final HolidayService holidayService;

    @GetMapping("/{year}/{country}")
    public List<LocalDate> getHolidays(@PathVariable int year, @PathVariable String country) {
        return holidayService.getHolidaysForYear(year, country);
    }
}
