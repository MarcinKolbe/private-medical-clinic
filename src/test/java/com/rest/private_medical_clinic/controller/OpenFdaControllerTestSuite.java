package com.rest.private_medical_clinic.controller;

import com.rest.private_medical_clinic.domain.dto.OpenFdaResponseDto;
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

import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(OpenFdaController.class)
public class OpenFdaControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExternalDataFacade externalDataFacade;

    @Test
    public void shouldGetDrugSpecification() throws Exception {
        //Given
        String drugName = "ASPIRIN";

        when(externalDataFacade.getDrugInfo(drugName)).thenReturn(new OpenFdaResponseDto.DrugLabelDto());

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/medicines/{drugName}", drugName)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
