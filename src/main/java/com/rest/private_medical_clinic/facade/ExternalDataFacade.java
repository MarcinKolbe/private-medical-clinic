package com.rest.private_medical_clinic.facade;

import com.rest.private_medical_clinic.domain.dto.OpenFdaResponseDto;
import com.rest.private_medical_clinic.service.HolidayService;
import com.rest.private_medical_clinic.service.OpenFdaService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ExternalDataFacade {

    private final OpenFdaService openFdaService;
    private final HolidayService holidayService;

    public OpenFdaResponseDto.DrugLabelDto getDrugInfo(String drugName) {
        return openFdaService.getDrug(drugName);
    }

    public List<LocalDate> getHolidays(int year, String country) {
        return holidayService.getHolidaysForYear(year, country);
    }
}
