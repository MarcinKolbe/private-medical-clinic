package com.rest.private_medical_clinic.service;

import com.rest.private_medical_clinic.domain.User;
import com.rest.private_medical_clinic.domain.dto.PasswordResetDto;
import com.rest.private_medical_clinic.domain.dto.UserDto;
import com.rest.private_medical_clinic.enums.UserRole;
import com.rest.private_medical_clinic.exception.UserNotFoundException;
import com.rest.private_medical_clinic.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public List<User> getUsersByRole (String role) {
        return userRepository.findAllByUserRole(UserRole.valueOf(role));

    }

    public User getUserById(long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }

    @Transactional
    public User updateUser(long userId, UserDto userDto) {
        User user = getUserById(userId);
        if (userDto.getUsername() != null) {
            user.setUsername(userDto.getUsername());
        }
        if (userDto.getMail() != null) {
            user.setMail(userDto.getMail());
        }
        if (userDto.getUserRole() != null) {
            user.setUserRole(userDto.getUserRole());
        }
        userRepository.save(user);
        return user;
    }

    @Transactional
    public void deleteUser(long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }

    @Transactional
    public void resetPassword(long userId, PasswordResetDto passwordResetDto) {
        User user = getUserById(userId);
        user.setPassword(passwordResetDto.getNewPassword());
        userRepository.save(user);
    }

    @Transactional
    public void blockUser(long userId) {
        User user = getUserById(userId);
        user.setBlocked(true);
        userRepository.save(user);
    }

    @Transactional
    public void unblockUser(long userId) {
        User user = getUserById(userId);
        user.setBlocked(false);
        userRepository.save(user);
    }
}
