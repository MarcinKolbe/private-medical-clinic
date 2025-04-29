package com.rest.private_medical_clinic.service;

import com.rest.private_medical_clinic.domain.User;
import com.rest.private_medical_clinic.domain.dto.PasswordResetDto;
import com.rest.private_medical_clinic.domain.dto.UserDto;
import com.rest.private_medical_clinic.enums.UserRole;
import com.rest.private_medical_clinic.exeption.UserNotFoundException;
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
    public User updateUser(long id, UserDto userDto) {
        User user = getUserById(id);
        user.setUsername(userDto.getUsername());
        user.setMail(userDto.getMail());
        user.setUserRole(userDto.getUserRole());
        user.setBlocked(userDto.isBlocked());
        userRepository.save(user);
        return user;
    }

    @Transactional
    public void deleteUser(long id) {
        User user = getUserById(id);
        userRepository.delete(user);
    }

    @Transactional
    public void resetPassword(long id, PasswordResetDto passwordResetDto) {
        User user = getUserById(id);
        user.setPassword(passwordResetDto.getNewPassword());
        userRepository.save(user);
    }

    @Transactional
    public void blockUser(long id) {
        User user = getUserById(id);
        user.setBlocked(true);
        userRepository.save(user);
    }

    @Transactional
    public void unblockUser(long id) {
        User user = getUserById(id);
        user.setBlocked(false);
        userRepository.save(user);
    }
}
