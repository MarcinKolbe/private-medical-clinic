package com.rest.private_medical_clinic.controller;

import com.rest.private_medical_clinic.facade.ExternalDataFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/holidays")
@RequiredArgsConstructor
public class HolidayController {

    private final ExternalDataFacade externalDataFacade;

    @GetMapping("/{year}/{country}")
    public ResponseEntity<List<LocalDate>> getHolidays(@PathVariable int year, @PathVariable String country) {
        return ResponseEntity.ok(externalDataFacade.getHolidays(year, country));
    }
}
