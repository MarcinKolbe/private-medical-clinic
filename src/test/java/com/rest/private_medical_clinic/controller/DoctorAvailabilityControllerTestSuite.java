package com.rest.private_medical_clinic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.private_medical_clinic.domain.dto.DoctorAvailabilityDto;
import com.rest.private_medical_clinic.facade.DoctorAvailabilityFacade;
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
import java.time.LocalTime;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringJUnitWebConfig
@WebMvcTest(DoctorAvailabilityController.class)
public class DoctorAvailabilityControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DoctorAvailabilityFacade doctorAvailabilityFacade;

    @Test
    public void shouldGetEmptyDoctorAvailabilityList() throws Exception {
        //Given
        when(doctorAvailabilityFacade.getAllDoctorAvailability()).thenReturn(List.of());

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/availability")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    public void shouldGetAllDoctorAvailability() throws Exception {
        //Given
        List<DoctorAvailabilityDto> doctorAvailabilityDtoList = List.of(new DoctorAvailabilityDto(1L, 1L,
                LocalDate.of(2025, 5, 5), LocalTime.of(10, 0, 0),
                LocalTime.of(10, 30, 0), true));
        when(doctorAvailabilityFacade.getAllDoctorAvailability()).thenReturn(doctorAvailabilityDtoList);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/availability")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    public void shouldGetDoctorAvailabilityById() throws Exception {
        //Given
        long availabilityId = 1L;
        DoctorAvailabilityDto doctorAvailabilityDto = new DoctorAvailabilityDto(1L, 1L,
                LocalDate.of(2025, 5, 5), LocalTime.of(10, 0, 0),
                LocalTime.of(10, 30, 0), true);
        when(doctorAvailabilityFacade.getDoctorAvailabilityById(availabilityId)).thenReturn(doctorAvailabilityDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/availability/{availabilityId}", availabilityId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.date", Matchers.is("2025-05-05")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endTime", Matchers.is("10:30:00")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.available", Matchers.is(true)));
    }

    @Test
    public void shouldGetDoctorAvailabilityByDoctorId() throws Exception {
        //Given
        long doctorId = 1L;
        List<DoctorAvailabilityDto> doctorAvailabilityDtoList = List.of(new DoctorAvailabilityDto(1L, 1L,
                LocalDate.of(2025, 5, 5), LocalTime.of(10, 0, 0),
                LocalTime.of(10, 30, 0), true));
        when(doctorAvailabilityFacade.getDoctorAvailabilityByDoctorId(doctorId)).thenReturn(doctorAvailabilityDtoList);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/availability/doctor/{doctorId}", doctorId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    public void shouldGetDoctorAvailableSlots() throws Exception {
        //Given
        long doctorId = 1L;
        List<DoctorAvailabilityDto> doctorAvailabilityDtoList = List.of(new DoctorAvailabilityDto(1L, 1L,
                LocalDate.of(2025, 5, 5), LocalTime.of(10, 0, 0),
                LocalTime.of(10, 30, 0), true));
        when(doctorAvailabilityFacade.getAvailableSlotsForDoctor(doctorId)).thenReturn(doctorAvailabilityDtoList);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/availability/{doctorId}/available-slots", doctorId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    public void shouldAddDoctorAvailability() throws Exception {
        //Given
        long doctorId = 1L;

        doNothing().when(doctorAvailabilityFacade).generateDoctorAvailability(doctorId);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/availability/{doctorId}", doctorId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void shouldUpdateDoctorAvailability() throws Exception {
        //Given
        DoctorAvailabilityDto doctorAvailabilityDto = new DoctorAvailabilityDto(1L, 1L,
                LocalDate.of(2025, 5, 5), LocalTime.of(10, 0, 0),
                LocalTime.of(10, 30, 0), true);
        when(doctorAvailabilityFacade.updateDoctorAvailability(doctorAvailabilityDto)).thenReturn(doctorAvailabilityDto);

        String jsonContent = objectMapper.writeValueAsString(doctorAvailabilityDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/availability")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.date", Matchers.is("2025-05-05")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.endTime", Matchers.is("10:30:00")));
    }

    @Test
    public void shouldDeleteDoctorAvailability() throws Exception {
        //Given
        long availabilityId = 1L;

        doNothing().when(doctorAvailabilityFacade).deleteDoctorAvailability(availabilityId);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/v1/availability/{availabilityId}", availabilityId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void shouldCheckOk() throws Exception {
        //Given
        long doctorId = 1L;
        LocalDate date = LocalDate.of(2025, 5, 5);
        LocalTime start = LocalTime.of(10, 0, 0);
        LocalTime end = LocalTime.of(10, 30, 0);
        String strategy = "defaultStrategy";
        boolean ok = true;

        when(doctorAvailabilityFacade.checkSlot(doctorId, date, start, end, strategy)).thenReturn(ok);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/availability/check?doctorId={doctorId}&date={date}&start={start}&end={end}&strategy={strategy}", doctorId, date, start, end, strategy)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    public void shouldCheckConflict() throws Exception {
        //Given
        long doctorId = 1L;
        LocalDate date = LocalDate.of(2025, 5, 5);
        LocalTime start = LocalTime.of(10, 0, 0);
        LocalTime end = LocalTime.of(10, 30, 0);
        String strategy = "defaultStrategy";
        boolean ok = false;

        when(doctorAvailabilityFacade.checkSlot(doctorId, date, start, end, strategy)).thenReturn(ok);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/availability/check?doctorId={doctorId}&date={date}&start={start}&end={end}&strategy={strategy}", doctorId, date, start, end, strategy)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isConflict());

    }
}
