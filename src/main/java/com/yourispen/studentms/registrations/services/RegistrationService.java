package com.yourispen.studentms.registrations.services;


import com.yourispen.studentms.registrations.dto.requests.RegistrationDtoRequest;
import com.yourispen.studentms.registrations.dto.responses.RegistrationDtoResponse;


import java.util.List;
import java.util.Optional;


public interface RegistrationService {
    Optional<RegistrationDtoResponse> saveRegistration(RegistrationDtoRequest registrationDto);
    List<RegistrationDtoResponse> getAllRegistrations();
    Optional<RegistrationDtoResponse> getRegistrationById(Long id);
    boolean deleteRegistration(Long id);
    Optional<RegistrationDtoResponse> updateRegistration(Long id, RegistrationDtoRequest registrationDto);
}
