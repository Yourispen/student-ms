package com.yourispen.studentms.academicyears.services.impl;


import com.yourispen.studentms.academicyears.dto.requests.AcademicYearDtoRequest;
import com.yourispen.studentms.academicyears.dto.responses.AcademicYearDtoResponse;
import com.yourispen.studentms.academicyears.entities.AcademicYearEntity;
import com.yourispen.studentms.academicyears.mapper.AcademicYearMapper;
import com.yourispen.studentms.academicyears.repository.AcademicYearRepository;
import com.yourispen.studentms.academicyears.services.AcademicYearService;
import com.yourispen.studentms.exception.EntityExistsException;
import com.yourispen.studentms.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Locale;
import java.util.Optional;


@Service
@AllArgsConstructor
@Slf4j
@Getter
@Setter
public class AcademicYearServiceImpl implements AcademicYearService {


    private static final String YEAR_NOT_FOUND = "academicYear.notfound";
    private static final String YEAR_EXISTS = "academicYear.exists";


    private final AcademicYearRepository academicYearRepository;
    private final AcademicYearMapper academicYearMapper;
    private final MessageSource messageSource;


    @Override
    @Transactional
    public Optional<AcademicYearDtoResponse> saveAcademicYear(AcademicYearDtoRequest request) {
        if (academicYearRepository.findByName(request.getName()).isPresent()) {
            throw new EntityExistsException(
                    messageSource.getMessage(YEAR_EXISTS, new Object[]{request.getName()}, Locale.getDefault()));
        }
        AcademicYearEntity year = academicYearMapper.toAcademicYearEntity(request);
        AcademicYearEntity savedYear = academicYearRepository.save(year);
        return Optional.of(academicYearMapper.toAcademicYearDtoResponse(savedYear));
    }


    @Override
    public Optional<AcademicYearDtoResponse> getAcademicYearById(Long id) {
        return academicYearRepository.findById(id)
                .map(academicYearMapper::toAcademicYearDtoResponse)
                .map(Optional::of)
                .orElseThrow(() -> new EntityNotFoundException(
                        messageSource.getMessage(YEAR_NOT_FOUND, new Object[]{id}, Locale.getDefault())));
    }


    @Override
    public List<AcademicYearDtoResponse> getAllAcademicYears() {
        List<AcademicYearEntity> years = academicYearRepository.findAll();
        return academicYearMapper.toAcademicYearDtoResponseList(years);
    }


    @Override
    public boolean deleteAcademicYear(Long id) {
        if (academicYearRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException(
                    messageSource.getMessage(YEAR_NOT_FOUND, new Object[]{id}, Locale.getDefault()));
        }
        academicYearRepository.deleteById(id);
        return true;
    }


    @Override
    public Optional<AcademicYearDtoResponse> updateAcademicYear(Long id, AcademicYearDtoRequest request) {
        return academicYearRepository.findById(id)
                .map(year -> {
                    year.setName(request.getName());
                    year.setDescription(request.getDescription());
                    year.setArchive(request.isArchive());
                    AcademicYearEntity updatedYear = academicYearRepository.save(year);
                    return Optional.of(academicYearMapper.toAcademicYearDtoResponse(updatedYear));
                }).orElseThrow(() -> new EntityNotFoundException(
                        messageSource.getMessage(YEAR_NOT_FOUND, new Object[]{id}, Locale.getDefault())));
    }
}
