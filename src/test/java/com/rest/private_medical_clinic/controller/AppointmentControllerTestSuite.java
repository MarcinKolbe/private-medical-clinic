package com.rest.private_medical_clinic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.private_medical_clinic.domain.dto.AppointmentDto;
import com.rest.private_medical_clinic.domain.dto.AppointmentRegistrationDto;
import com.rest.private_medical_clinic.domain.dto.AppointmentRescheduleDto;
import com.rest.private_medical_clinic.enums.AppointmentStatus;
import com.rest.private_medical_clinic.facade.AppointmentFacade;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(AppointmentController.class)
public class AppointmentControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private AppointmentFacade appointmentFacade;

    @Test
    public void shouldGetEmptyAppointmentsList() throws Exception {
        //Given
        when(appointmentFacade.getAllAppointments()).thenReturn(List.of());

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/appointments")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    public void shouldGetAppointmentsList() throws Exception {
        //Given
        List<AppointmentDto> appointmentsDto = List.of(new AppointmentDto(1L, 1L, 2L,
                LocalDate.of(2025, 5, 5), LocalTime.of(10, 0, 0),
                AppointmentStatus.SCHEDULED, "Initial visit", 0));
        when(appointmentFacade.getAllAppointments()).thenReturn(appointmentsDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/appointments")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    public void shouldGetAppointmentById() throws Exception {
        //Given
        AppointmentDto appointmentDto = new AppointmentDto(1L, 1L, 2L,
                LocalDate.of(2025, 5, 5), LocalTime.of(10, 0, 0),
                AppointmentStatus.SCHEDULED, "Initial visit", 0);
        when(appointmentFacade.getAppointmentById(1L)).thenReturn(appointmentDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                .get("/v1/appointments/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.doctorId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.patientId", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is("SCHEDULED")));
    }

    @Test
    public void shouldCreateAppointment() throws Exception {
        //Given
        AppointmentDto appointmentDto = new AppointmentDto(1L, 1L, 2L,
                LocalDate.of(2025, 5, 5), LocalTime.of(10, 0, 0),
                AppointmentStatus.SCHEDULED, "Initial visit", 0);
        AppointmentRegistrationDto appointmentRegistrationDto = new AppointmentRegistrationDto(1L, 2L,
                LocalDate.of(2025, 5, 5), LocalTime.of(10, 0), "Initial visit");
        when(appointmentFacade.createAppointment(any(AppointmentRegistrationDto.class))).thenReturn(appointmentDto);

        String jsonContent = objectMapper.writeValueAsString(appointmentRegistrationDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                .post("/v1/appointments/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.doctorId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.patientId", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.notes", Matchers.is("Initial visit")));
    }

    @Test
    public void shouldCancelAppointment() throws Exception {
        //Given
        AppointmentDto appointmentDto = new AppointmentDto(1L, 1L, 2L,
                LocalDate.of(2025, 5, 5), LocalTime.of(10, 0, 0),
                AppointmentStatus.SCHEDULED, "Initial visit", 0);
        long id = appointmentDto.getId();
        doNothing().when(appointmentFacade).cancelAppointment(id);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                .put("/v1/appointments/{id}/cancel", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

    }

    @Test
    public void shouldRescheduleAppointment() throws Exception {
        //Given
        long id = 1L;
        AppointmentDto appointmentDto = new AppointmentDto(1L, 1L, 2L,
                LocalDate.of(2025, 5, 5), LocalTime.of(10, 0, 0),
                AppointmentStatus.SCHEDULED, "Initial visit", 0);
        AppointmentRescheduleDto appointmentRescheduleDto = new AppointmentRescheduleDto(
                LocalDate.of(2025, 5, 5), LocalTime.of(11, 0, 0));
        AppointmentDto rescheduledAppointment = new AppointmentDto(1L, 1L, 2L,
                LocalDate.of(2025, 5, 5), LocalTime.of(11, 0, 0),
                AppointmentStatus.RESCHEDULED, "Initial visit", 0);
        when(appointmentFacade.rescheduleAppointment(id, appointmentRescheduleDto)).thenReturn(rescheduledAppointment);

        String jsonContent = objectMapper.writeValueAsString(appointmentRescheduleDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                .put("/v1/appointments/{id}/reschedule", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is("RESCHEDULED")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.time", Matchers.is("11:00:00")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.date", Matchers.is("2025-05-05")));
    }

    @Test
    public void shouldDeleteAppointment() throws Exception {
        //Given
        long id = 1L;

        doNothing().when(appointmentFacade).deleteAppointment(id);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                .delete("/v1/appointments/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
