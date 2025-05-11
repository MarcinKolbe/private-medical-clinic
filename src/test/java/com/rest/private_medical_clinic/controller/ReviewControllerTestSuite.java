package com.rest.private_medical_clinic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.private_medical_clinic.domain.dto.ReviewDto;
import com.rest.private_medical_clinic.facade.ReviewFacade;
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

import java.time.LocalDateTime;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(ReviewController.class)
public class ReviewControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ReviewFacade reviewFacade;

    @Test
    public void shouldGetEmptyReviewsList() throws Exception {
        //Given
        when(reviewFacade.getAllReviews()).thenReturn(List.of());

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/reviews")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    public void shouldGetAllReviews() throws Exception {
        //Given
        List<ReviewDto> reviewDtoList = List.of(new ReviewDto(1L, 1L, 5,
                "very nice", LocalDateTime.now()));
        when(reviewFacade.getAllReviews()).thenReturn(reviewDtoList);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/reviews")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    public void shouldGetReviewById() throws Exception {
        //Given
        long reviewId = 1L;
        ReviewDto reviewDto = new ReviewDto(1L, 1L, 5, "very nice",
                LocalDateTime.now());
        when(reviewFacade.getReviewById(reviewId)).thenReturn(reviewDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/reviews/{reviewId}", reviewId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.reviewId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.comment", Matchers.is("very nice")));
    }

    @Test
    public void shouldGetReviewsByDoctorId() throws Exception {
        //Given
        long doctorId = 1L;
        List<ReviewDto> reviewDtoList = List.of(new ReviewDto(1L, 1L, 5,
                "very nice", LocalDateTime.now()));
        when(reviewFacade.getReviewsByDoctorId(doctorId)).thenReturn(reviewDtoList);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/reviews/doctor/{doctorId}", doctorId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].reviewId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].comment", Matchers.is("very nice")));
    }

    @Test
    public void shouldGetReviewsByPatientId() throws Exception {
        //Given
        long patientId = 1L;
        List<ReviewDto> reviewDtoList = List.of(new ReviewDto(1L, 1L, 5,
                "very nice", LocalDateTime.now()));
        when(reviewFacade.getReviewsByPatientId(patientId)).thenReturn(reviewDtoList);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/reviews/patient/{patientId}", patientId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].reviewId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.[0].comment", Matchers.is("very nice")));
    }

    @Test
    public void shouldCreateReview() throws Exception {
        //Given
        ReviewDto reviewDto = new ReviewDto(1L, 1L, 5, "very nice",
                LocalDateTime.now());
        when(reviewFacade.createReview(reviewDto)).thenReturn(reviewDto);

        String jsonContent = objectMapper.writeValueAsString(reviewDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/reviews")
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.reviewId", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.comment", Matchers.is("very nice")));
    }

    @Test
    public void shouldDeleteReview() throws Exception {
        //Given
        long reviewId = 1L;

        doNothing().when(reviewFacade).deleteReview(reviewId);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/v1/reviews/{reviewId}", reviewId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
