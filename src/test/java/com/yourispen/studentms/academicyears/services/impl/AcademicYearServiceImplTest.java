package com.yourispen.studentms.academicyears.services.impl;

import com.yourispen.studentms.academicyears.dto.requests.AcademicYearDtoRequest;
import com.yourispen.studentms.academicyears.dto.responses.AcademicYearDtoResponse;
import com.yourispen.studentms.academicyears.entities.AcademicYearEntity;
import com.yourispen.studentms.academicyears.mapper.AcademicYearMapper;
import com.yourispen.studentms.academicyears.repository.AcademicYearRepository;
import com.yourispen.studentms.exception.EntityExistsException;
import com.yourispen.studentms.exception.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.MessageSource;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AcademicYearServiceImplTest {

    @Mock
    private AcademicYearRepository academicYearRepository;

    @InjectMocks
    private AcademicYearServiceImpl academicYearService;

    @Mock
    private AcademicYearMapper academicYearMapper;

    @Mock
    private MessageSource messageSource;

    @Test
    void saveAcademicYearOK() {
        when(academicYearRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(academicYearMapper.toAcademicYearEntity(any())).thenReturn(getAcademicYearEntity());
        when(academicYearRepository.save(any())).thenReturn(getAcademicYearEntity());
        when(academicYearMapper.toAcademicYearDtoResponse(any())).thenReturn(getAcademicYearDtoResponse());

        Optional<AcademicYearDtoResponse> academicYearResponse = academicYearService.saveAcademicYear(getAcademicYearDtoRequest());

        assertTrue(academicYearResponse.isPresent());
        assertEquals("2024-2025", academicYearResponse.get().getName());
    }

   /* @Test
    void saveAcademicYearKO() {
        when(academicYearRepository.findByName(anyString())).thenReturn(Optional.of(getAcademicYearEntity()));
        when(messageSource.getMessage(eq("exists"), any(Object[].class), any(Locale.class)))
                .thenReturn("L'année académique 2024-2025 existe déjà");

        EntityExistsException exception = assertThrows(EntityExistsException.class,
                () -> academicYearService.saveAcademicYear(getAcademicYearDtoRequest()));

        assertEquals("L'année académique 2024-2025 existe déjà", exception.getMessage());
    }*/

    @Test
    void getAllAcademicYears() {
        when(academicYearRepository.findAll()).thenReturn(List.of(getAcademicYearEntity()));
        when(academicYearMapper.toAcademicYearDtoResponseList(any())).thenReturn(List.of(getAcademicYearDtoResponse()));

        List<AcademicYearDtoResponse> academicYears = academicYearService.getAllAcademicYears();

        assertFalse(academicYears.isEmpty());
        assertEquals(1, academicYears.size());
    }

    @Test
    void getAcademicYearByIdOK() {
        when(academicYearRepository.findById(anyLong())).thenReturn(Optional.of(getAcademicYearEntity()));
        when(academicYearMapper.toAcademicYearDtoResponse(any())).thenReturn(getAcademicYearDtoResponse());

        Optional<AcademicYearDtoResponse> academicYearResponse = academicYearService.getAcademicYearById(1L);

        assertTrue(academicYearResponse.isPresent());
        assertEquals("2024-2025", academicYearResponse.get().getName());
    }

    /*@Test
    void getAcademicYearByIdKO() {
        when(academicYearRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("academicyear.notfound"), any(Object[].class), any(Locale.class)))
                .thenReturn("Année académique non trouvée");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> academicYearService.getAcademicYearById(1L));

        assertEquals("Année académique non trouvée", exception.getMessage());
    }*/

    @Test
    void deleteAcademicYearOK() {
        when(academicYearRepository.findById(anyLong())).thenReturn(Optional.of(getAcademicYearEntity()));

        boolean result = academicYearService.deleteAcademicYear(1L);

        assertTrue(result);
        verify(academicYearRepository, times(1)).deleteById(anyLong());
    }

    /*@Test
    void deleteAcademicYearKO() {
        when(academicYearRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("academicyear.notfound"), any(Object[].class), any(Locale.class)))
                .thenReturn("Année académique non trouvée");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> academicYearService.deleteAcademicYear(1L));

        assertEquals("Année académique non trouvée", exception.getMessage());
    }*/

    @Test
    void updateAcademicYearOK() {
        when(academicYearRepository.findById(anyLong())).thenReturn(Optional.of(getAcademicYearEntity()));
        when(academicYearRepository.save(any())).thenReturn(getAcademicYearEntity());
        when(academicYearMapper.toAcademicYearDtoResponse(any())).thenReturn(getAcademicYearDtoResponse());

        Optional<AcademicYearDtoResponse> updatedAcademicYear = academicYearService.updateAcademicYear(1L, getAcademicYearDtoRequest());

        assertTrue(updatedAcademicYear.isPresent());
        assertEquals("2024-2025", updatedAcademicYear.get().getName());
    }

    /*@Test
    void updateAcademicYearKO() {
        when(academicYearRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("academicyear.notfound"), any(Object[].class), any(Locale.class)))
                .thenReturn("Année académique non trouvée");

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> academicYearService.updateAcademicYear(1L, getAcademicYearDtoRequest()));

        assertEquals("Année académique non trouvée", exception.getMessage());
    }*/

    private AcademicYearDtoRequest getAcademicYearDtoRequest() {
        AcademicYearDtoRequest request = new AcademicYearDtoRequest();
        request.setName("2024-2025");
        request.setDescription("Année académique 2024-2025");
        request.setArchive(false);
        return request;
    }

    private AcademicYearEntity getAcademicYearEntity() {
        AcademicYearEntity entity = new AcademicYearEntity();
        entity.setId(1L);
        entity.setName("2024-2025");
        entity.setDescription("Année académique 2024-2025");
        entity.setArchive(false);
        return entity;
    }

    private AcademicYearDtoResponse getAcademicYearDtoResponse() {
        AcademicYearDtoResponse response = new AcademicYearDtoResponse();
        response.setId(1L);
        response.setName("2024-2025");
        response.setDescription("Année académique 2024-2025");
        response.setArchive(false);
        return response;
    }
}



