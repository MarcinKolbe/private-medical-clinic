package com.rest.private_medical_clinic.controller;

import com.rest.private_medical_clinic.facade.ExternalDataFacade;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.List;

import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(HolidayController.class)
public class HolidayControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExternalDataFacade externalDataFacade;

    @Test
    public void shouldGetHolidays() throws Exception {
        //Given
        int year = 2025;
        String country = "PL";

        when(externalDataFacade.getHolidays(year, country)).thenReturn(List.of());

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/holidays/{year}/{country}", year, country)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
