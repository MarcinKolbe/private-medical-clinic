package com.rest.private_medical_clinic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.private_medical_clinic.domain.dto.PatientDto;
import com.rest.private_medical_clinic.domain.dto.PatientRegistrationDto;
import com.rest.private_medical_clinic.facade.PatientFacade;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringJUnitWebConfig
@WebMvcTest(PatientController.class)
public class PatientControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PatientFacade patientFacade;

    @Test
    public void shouldGetEmptyPatientsList() throws Exception {
        //Given
        when(patientFacade.getAllPatients()).thenReturn(List.of());

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/patients")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    public void shouldGetAllPatients() throws Exception {
        //Given
        List<PatientDto> patientDtoList = List.of(new PatientDto(2L, "Jan", "Kowalski",
                500500500, "58062411204", LocalDate.of(1958, 6, 24),
                2L, new ArrayList<>(), new ArrayList<>()));
        when(patientFacade.getAllPatients()).thenReturn(patientDtoList);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/patients")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    public void shouldGetPatientById() throws Exception {
        //Given
        long patientId = 2L;
        PatientDto patientDto = new PatientDto(2L, "Jan", "Kowalski", 500500500,
                "58062411204", LocalDate.of(1958, 6, 24), 2L,
                new ArrayList<>(), new ArrayList<>());
        when(patientFacade.getPatientById(patientId)).thenReturn(patientDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/patients/{patientId}", patientId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstname", Matchers.is("Jan")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastname", Matchers.is("Kowalski")));
    }

    @Test
    public void shouldRegisterPatient() throws Exception {
        //Given
        PatientRegistrationDto patientRegistrationDto = new PatientRegistrationDto("janek58",
                "password", "janek58@mail.com", "Jan", "Kowalski", 500500500,
                "58062411204", LocalDate.of(1958, 6, 24));
        doNothing().when(patientFacade).registerPatient(patientRegistrationDto);

        String jsonContent = objectMapper.writeValueAsString(patientRegistrationDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void shouldUpdatePatient() throws Exception {
        //Given
        PatientDto patientDto = new PatientDto(2L, "Jan", "Kowalski", 500500500,
                "58062411204", LocalDate.of(1958, 6, 24), 2L,
                new ArrayList<>(), new ArrayList<>());
        when(patientFacade.updatePatient(patientDto)).thenReturn(patientDto);

        String jsonContent = objectMapper.writeValueAsString(patientDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstname", Matchers.is("Jan")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastname", Matchers.is("Kowalski")));
    }

    @Test
    public void shouldDeletePatient() throws Exception {
        //Given
        long patientId = 2L;

        doNothing().when(patientFacade).deletePatient(patientId);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                .delete("/v1/patients/{patientId}", patientId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
