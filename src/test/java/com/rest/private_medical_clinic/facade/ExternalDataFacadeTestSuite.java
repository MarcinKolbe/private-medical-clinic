package com.rest.private_medical_clinic.facade;

import com.rest.private_medical_clinic.service.HolidayService;
import com.rest.private_medical_clinic.service.OpenFdaService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ExternalDataFacadeTestSuite {

    @InjectMocks
    private ExternalDataFacade externalDataFacade;

    @Mock
    private OpenFdaService openFdaService;

    @Mock
    private HolidayService holidayService;

    @Test
    public void shouldGetEmptyHolidayList() {
        //Given
        int year = 2025;
        String country = "PL";

        when(holidayService.getHolidaysForYear(year,country)).thenReturn(List.of());

        //When
        List<LocalDate> holidays = externalDataFacade.getHolidays(year,country);

        //Then
        assertEquals(0, holidays.size());
    }

    @Test
    public void shouldGetHolidaysForYear() {
        //Given
        int year = 2025;
        String country = "PL";

        when(holidayService.getHolidaysForYear(year,country)).thenReturn(List.of(LocalDate.of(2025,1,1)
        ,LocalDate.of(2025,2,1), LocalDate.of(2025,3,1)));

        //When
        List<LocalDate> holidays = externalDataFacade.getHolidays(year,country);

        //Then
        assertEquals(3, holidays.size());
        assertEquals(LocalDate.of(2025,1,1), holidays.get(0));
    }
}
