package com.rest.private_medical_clinic.repository;

import com.rest.private_medical_clinic.domain.User;
import com.rest.private_medical_clinic.enums.UserRole;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    @Override
    List<User> findAll();

    List<User> findAllByUserRole(UserRole userRole);

}
