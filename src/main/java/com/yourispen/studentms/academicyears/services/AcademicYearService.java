package com.yourispen.studentms.academicyears.services;


import com.yourispen.studentms.academicyears.dto.requests.AcademicYearDtoRequest;
import com.yourispen.studentms.academicyears.dto.responses.AcademicYearDtoResponse;


import java.util.List;
import java.util.Optional;


public interface AcademicYearService {
    Optional<AcademicYearDtoResponse> saveAcademicYear(AcademicYearDtoRequest request);
    Optional<AcademicYearDtoResponse> updateAcademicYear(Long id, AcademicYearDtoRequest request);
    Optional<AcademicYearDtoResponse> getAcademicYearById(Long id);
    List<AcademicYearDtoResponse> getAllAcademicYears();
    boolean deleteAcademicYear(Long id);
}
