package com.rest.private_medical_clinic.controller;

import com.rest.private_medical_clinic.domain.dto.PasswordResetDto;
import com.rest.private_medical_clinic.domain.dto.UserDto;
import com.rest.private_medical_clinic.facade.UserFacade;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/v1/users")
@Validated
@RequiredArgsConstructor
public class UserController {

    private final UserFacade userFacade;
    private final Logger LOGGER = LoggerFactory.getLogger(UserController.class);

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(@RequestParam(required = false) String role) {
        List<UserDto> users = (role == null)
                ? userFacade.getAllUsers()
                : userFacade.getUsersByRole(role);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable long userId) {
        return ResponseEntity.ok(userFacade.getUserById(userId));
    }

    @PutMapping(value = "/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> updateUser(@PathVariable long userId, @Valid @RequestBody UserDto userDto) {
        LOGGER.info("Incoming update request UserDto: {}", userDto);
        return ResponseEntity.ok(userFacade.updateUser(userId, userDto));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable long userId) {
        userFacade.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = ("/{userId}/reset-password"), consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> resetPassword(@PathVariable long userId, @Valid @RequestBody PasswordResetDto passwordResetDto) {
        LOGGER.info("The user {} has reset their password.", userId);
        userFacade.resetPassword(userId, passwordResetDto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{userId}/block")
    public ResponseEntity<Void> blockUser(@PathVariable long userId) {
        userFacade.blockUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{userId}/unblock")
    public ResponseEntity<Void> unblockUser(@PathVariable long userId) {
        userFacade.unblockUser(userId);
        return ResponseEntity.noContent().build();
    }
}
