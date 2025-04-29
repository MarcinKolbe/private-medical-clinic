package com.rest.private_medical_clinic.mapper;

import com.rest.private_medical_clinic.domain.User;
import com.rest.private_medical_clinic.domain.dto.UserDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserMapper {

    public UserDto mapToDto (User user) {
        return new UserDto(user.getId(), user.getUsername(), user.getMail(), user.getUserRole(), user.isBlocked());
    }

    public List<UserDto> mapToDtoList (List<User> users) {
        return users.stream()
                .map(this::mapToDto)
                .toList();
    }
}
