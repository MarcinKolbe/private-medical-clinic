package com.rest.private_medical_clinic.domain.dto;

import com.rest.private_medical_clinic.enums.UserRole;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    private long id;
    private String username;
    private String mail;
    private UserRole userRole;
    private boolean blocked;

}
