package com.rest.private_medical_clinic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.private_medical_clinic.domain.dto.DoctorScheduleTemplateDto;
import com.rest.private_medical_clinic.facade.DoctorScheduleTemplateFacade;
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

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

import static org.mockito.Mockito.*;

@SpringJUnitWebConfig
@WebMvcTest(DoctorScheduleTemplateController.class)
public class DoctorScheduleTemplateControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private DoctorScheduleTemplateFacade doctorScheduleTemplateFacade;

    @Test
    public void shouldGetEmptyDoctorScheduleTemplatesList() throws Exception {
        //Given
        when(doctorScheduleTemplateFacade.getAllDoctorScheduleTemplates()).thenReturn(List.of());

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/schedule-template")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    public void shouldGetAllDoctorScheduleTemplates() throws Exception {
        //Given
        List<DoctorScheduleTemplateDto> dtoTemplates = List.of(new DoctorScheduleTemplateDto(1L, 1L,
                DayOfWeek.MONDAY, LocalTime.of(10, 0, 0), LocalTime.of(15, 0, 0)));
        when(doctorScheduleTemplateFacade.getAllDoctorScheduleTemplates()).thenReturn(dtoTemplates);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/schedule-template")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    public void shouldGetDoctorScheduleTemplate() throws Exception {
        //Given
        long templateId = 1L;
        DoctorScheduleTemplateDto templateDto = new DoctorScheduleTemplateDto(1L, 1L, DayOfWeek.MONDAY,
                LocalTime.of(10, 0, 0), LocalTime.of(15, 0, 0));
        when(doctorScheduleTemplateFacade.getDoctorScheduleTemplateById(templateId)).thenReturn(templateDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/schedule-template/{templateId}", templateId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dayOfWeek", Matchers.is("MONDAY")));
    }

    @Test
    public void shouldDeleteDoctorScheduleTemplate() throws Exception {
        //Given
        long templateId = 1L;

        doNothing().when(doctorScheduleTemplateFacade).deleteDoctorScheduleTemplateById(templateId);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/v1/schedule-template/{templateId}", templateId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void shouldCreateDoctorScheduleTemplate() throws Exception {
        //Given
        long doctorId = 1L;
        DoctorScheduleTemplateDto templateDto = new DoctorScheduleTemplateDto(1L, 1L, DayOfWeek.MONDAY,
                LocalTime.of(10, 0, 0), LocalTime.of(15, 0, 0));
        when(doctorScheduleTemplateFacade.createDoctorScheduleTemplate(doctorId, templateDto)).thenReturn(templateDto);

        String jsonContent = objectMapper.writeValueAsString(templateDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/schedule-template/doctor/{doctorId}", doctorId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dayOfWeek", Matchers.is("MONDAY")));
    }

    @Test
    public void shouldGetTemplatesByDoctor() throws Exception {
        //Given
        long doctorId = 1L;
        List<DoctorScheduleTemplateDto> dtoTemplates = List.of(new DoctorScheduleTemplateDto(1L, 1L,
                DayOfWeek.MONDAY, LocalTime.of(10, 0, 0), LocalTime.of(15, 0, 0)));
        when(doctorScheduleTemplateFacade.getDoctorScheduleTemplatesByDoctorId(doctorId)).thenReturn(dtoTemplates);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/schedule-template/doctor/{doctorId}", doctorId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    public void shouldUpdateDoctorScheduleTemplate() throws Exception {
        //Given
        long templateId = 1L;
        DoctorScheduleTemplateDto templateDto = new DoctorScheduleTemplateDto(1L, 1L, DayOfWeek.MONDAY,
                LocalTime.of(10, 0, 0), LocalTime.of(15, 0, 0));
        when(doctorScheduleTemplateFacade.updateDoctorScheduleTemplate(templateId, templateDto)).thenReturn(templateDto);

        String jsonContent = objectMapper.writeValueAsString(templateDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                .put("/v1/schedule-template/{templateId}", templateId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.dayOfWeek", Matchers.is("MONDAY")));
    }
}
