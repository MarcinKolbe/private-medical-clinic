package com.rest.private_medical_clinic.facade;

import com.rest.private_medical_clinic.domain.User;
import com.rest.private_medical_clinic.domain.dto.PasswordResetDto;
import com.rest.private_medical_clinic.domain.dto.UserDto;
import com.rest.private_medical_clinic.mapper.UserMapper;
import com.rest.private_medical_clinic.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserFacade {

    private final UserService userService;
    private final UserMapper userMapper;

    public List<UserDto> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return userMapper.mapToDtoList(users);
    }

    public List<UserDto> getUsersByRole(String role) {
        List<User> users = userService.getUsersByRole(role);
        return userMapper.mapToDtoList(users);
    }

    public UserDto getUserById(long userId) {
        User user = userService.getUserById(userId);
        return userMapper.mapToDto(user);
    }

    public UserDto updateUser(long userId, UserDto userDto) {
        User user = userService.updateUser(userId, userDto);
        return userMapper.mapToDto(user);
    }

    public void deleteUser(long userId) {
        userService.deleteUser(userId);
    }

    public void resetPassword(long userId, PasswordResetDto passwordResetDto) {
        userService.resetPassword(userId, passwordResetDto);
    }

    public void blockUser(long userId) {
        userService.blockUser(userId);
    }

    public void unblockUser(long userId) {
        userService.unblockUser(userId);
    }
}
