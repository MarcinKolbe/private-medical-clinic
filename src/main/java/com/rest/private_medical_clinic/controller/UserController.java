package com.rest.private_medical_clinic.controller;

import com.rest.private_medical_clinic.domain.User;
import com.rest.private_medical_clinic.domain.dto.PasswordResetDto;
import com.rest.private_medical_clinic.domain.dto.UserDto;
import com.rest.private_medical_clinic.mapper.UserMapper;
import com.rest.private_medical_clinic.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
@Validated
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers(@RequestParam(required = false) String role) {
        List<User> users = (role == null)
                ? userService.getAllUsers()
                : userService.getUsersByRole(role);
        return ResponseEntity.ok(userMapper.mapToDtoList(users));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable long userId) {
        User user = userService.getUserById(userId);
        return ResponseEntity.ok(userMapper.mapToDto(user));
    }

    @PutMapping(value = "/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserDto> updateUser(@PathVariable long userId, @Valid @RequestBody UserDto userDto) {
        User updatedUser = userService.updateUser(userId, userDto);
        return ResponseEntity.ok(userMapper.mapToDto(updatedUser));
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = ("/{userId}/reset-password"), consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> resetPassword(@PathVariable long userId, @Valid @RequestBody PasswordResetDto passwordResetDto) {
        userService.resetPassword(userId, passwordResetDto);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{userId}/block")
    public ResponseEntity<Void> blockUser(@PathVariable long userId) {
        userService.blockUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{userId}/unblock")
    public ResponseEntity<Void> unblockUser(@PathVariable long userId) {
        userService.unblockUser(userId);
        return ResponseEntity.noContent().build();
    }
}
