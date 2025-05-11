package com.rest.private_medical_clinic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.private_medical_clinic.domain.dto.DoctorDto;
import com.rest.private_medical_clinic.domain.dto.DoctorRegistrationDto;
import com.rest.private_medical_clinic.facade.DoctorFacade;
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

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(DoctorController.class)
public class DoctorControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DoctorFacade doctorFacade;

    @Test
    public void shouldGetEmptyDoctorList() throws Exception {
        //Given
        when(doctorFacade.getAllDoctors()).thenReturn(List.of());

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/doctors")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    public void shouldGetAllDoctors() throws Exception {
        //Given
        List<DoctorDto> doctors = List.of(new DoctorDto(1L, "Gregory", "House",
                "Diagnostics", 5, 1L, new ArrayList<>(), new ArrayList<>()));

        when(doctorFacade.getAllDoctors()).thenReturn(doctors);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/doctors")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    public void shouldGetDoctorById() throws Exception {
        //Given
        long doctorId = 1L;
        DoctorDto doctorDto = new DoctorDto(1L, "Gregory", "House",
                "Diagnostics", 5, 1L, new ArrayList<>(), new ArrayList<>());
        when(doctorFacade.getDoctorById(doctorId)).thenReturn(doctorDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/doctors/{doctorId}", doctorId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstname", Matchers.is("Gregory")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastname", Matchers.is("House")));
    }

    @Test
    public void shouldRegisterDoctor() throws Exception {
        //Given
        DoctorRegistrationDto doctorRegistrationDto = new DoctorRegistrationDto("docH", "password",
                "docH@mail.com", "Gregory", "House", "Diagnostics");
        doNothing().when(doctorFacade).registerDoctor(doctorRegistrationDto);

        String jsonContent = objectMapper.writeValueAsString(doctorRegistrationDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                .post("/v1/doctors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void shouldUpdateDoctor() throws Exception {
        //Given
        DoctorDto doctorDto = new DoctorDto(1L, "Gregory", "House",
                "Diagnostics", 5, 1L, new ArrayList<>(), new ArrayList<>());
        when(doctorFacade.updateDoctor(doctorDto)).thenReturn(doctorDto);

        String jsonContent = objectMapper.writeValueAsString(doctorDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                .put("/v1/doctors")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstname", Matchers.is("Gregory")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastname", Matchers.is("House")));
    }

    @Test
    public void shouldDeleteDoctor() throws Exception {
        //Given
        long doctorId = 1L;

        doNothing().when(doctorFacade).deleteDoctor(doctorId);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                .delete("/v1/doctors/{doctorId}", doctorId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
