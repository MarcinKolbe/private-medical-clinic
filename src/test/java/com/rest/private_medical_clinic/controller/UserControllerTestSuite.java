package com.rest.private_medical_clinic.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rest.private_medical_clinic.domain.dto.PasswordResetDto;
import com.rest.private_medical_clinic.domain.dto.UserDto;
import com.rest.private_medical_clinic.enums.UserRole;
import com.rest.private_medical_clinic.facade.UserFacade;
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

import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(UserController.class)
public class UserControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private UserFacade userFacade;

    @Test
    public void shouldGetEmptyUserList() throws Exception {
        //Given
        when(userFacade.getAllUsers()).thenReturn(List.of());

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(0)));
    }

    @Test
    public void shouldGetAllUsers() throws Exception {
        //Given
        List<UserDto> userDtoList = List.of(new UserDto(1L, "docH", "docH@mamil.com",
                UserRole.DOCTOR, false));
        when(userFacade.getAllUsers()).thenReturn(userDtoList);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(1)));
    }

    @Test
    public void shouldGetUserById() throws Exception {
        //Given
        long userId = 1L;
        UserDto userDto = new UserDto(1L, "docH", "docH@mamil.com",
                UserRole.DOCTOR, false);
        when(userFacade.getUserById(userId)).thenReturn(userDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username", Matchers.is("docH")));
    }

    @Test
    public void shouldUpdateUser() throws Exception {
        //Given
        long userId = 1L;
        UserDto userDto = new UserDto(1L, "docH", "docH@mamil.com",
                UserRole.DOCTOR, false);
        when(userFacade.updateUser(userId, userDto)).thenReturn(userDto);

        String jsonContent = objectMapper.writeValueAsString(userDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.username", Matchers.is("docH")));
    }

    @Test
    public void shouldDeleteUser() throws Exception {
        //Given
        long userId = 1L;

        doNothing().when(userFacade).deleteUser(userId);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .delete("/v1/users/{userId}", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void shouldResetPassword() throws Exception {
        //Given
        long userId = 1L;
        PasswordResetDto passwordResetDto = new PasswordResetDto("password");

        doNothing().when(userFacade).resetPassword(userId, passwordResetDto);

        String jsonContent = objectMapper.writeValueAsString(passwordResetDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/users/{userId}/reset-password", userId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .characterEncoding("UTF-8")
                        .content(jsonContent))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void shouldBlockUser() throws Exception {
        //Given
        long userId = 1L;

        doNothing().when(userFacade).blockUser(userId);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/users/{userId}/block", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    public void shouldUnBlockUser() throws Exception {
        //Given
        long userId = 1L;

        doNothing().when(userFacade).unblockUser(userId);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/users/{userId}/unblock", userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
