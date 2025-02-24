package com.yourispen.studentms.classes.services.impl;


import com.yourispen.studentms.classes.dto.requests.ClassDtoRequest;
import com.yourispen.studentms.classes.dto.responses.ClassDtoResponse;
import com.yourispen.studentms.classes.entities.ClassEntity;
import com.yourispen.studentms.classes.mapper.ClassMapper;
import com.yourispen.studentms.classes.repository.ClassRepository;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class ClassServiceImplTest {


    @Mock
    private ClassRepository classRepository;


    @InjectMocks
    private ClassServiceImpl classService;


    @Mock
    private ClassMapper classMapper;


    @Mock
    private MessageSource messageSource;


    @Test
    void saveClassOK() {
        when(classRepository.findByName(anyString())).thenReturn(Optional.empty());
        when(classMapper.toClassEntity(any())).thenReturn(getClassEntity());
        when(classRepository.save(any())).thenReturn(getClassEntity());
        when(classMapper.toClassDtoResponse(any())).thenReturn(getClassDtoResponse());


        Optional<ClassDtoResponse> classResponse = classService.saveClass(getClassDtoRequest());


        assertTrue(classResponse.isPresent());
        assertEquals("Terminale S", classResponse.get().getName());
    }


    @Test
    void saveClassKO() {
        when(classRepository.findByName(anyString())).thenReturn(Optional.of(getClassEntity()));
        when(messageSource.getMessage(eq("class.exists"), any(), any(Locale.class)))
                .thenReturn("La classe Terminale S existe déjà");


        EntityExistsException exception = assertThrows(EntityExistsException.class,
                () -> classService.saveClass(getClassDtoRequest()));


        assertEquals("La classe Terminale S existe déjà", exception.getMessage());
    }


    @Test
    void getAllClasses() {
        when(classRepository.findAll()).thenReturn(List.of(getClassEntity()));
        when(classMapper.toClassDtoResponseList(any())).thenReturn(List.of(getClassDtoResponse()));


        List<ClassDtoResponse> classes = classService.getAllClasses();


        assertFalse(classes.isEmpty());
        assertEquals(1, classes.size());
    }


    @Test
    void getClassByIdOK() {
        when(classRepository.findById(anyLong())).thenReturn(Optional.of(getClassEntity()));
        when(classMapper.toClassDtoResponse(any())).thenReturn(getClassDtoResponse());


        Optional<ClassDtoResponse> classResponse = classService.getClassById(1L);


        assertTrue(classResponse.isPresent());
        assertEquals("Terminale S", classResponse.get().getName());
    }


    @Test
    void getClassByIdKO() {
        when(classRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("class.notfound"), any(), any(Locale.class)))
                .thenReturn("Classe non trouvée");


        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> classService.getClassById(1L));


        assertEquals("Classe non trouvée", exception.getMessage());
    }


    @Test
    void deleteClassOK() {
        when(classRepository.findById(anyLong())).thenReturn(Optional.of(getClassEntity()));


        boolean result = classService.deleteClass(1L);


        assertTrue(result);
        verify(classRepository, times(1)).deleteById(anyLong());
    }


    @Test
    void deleteClassKO() {
        when(classRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("class.notfound"), any(), any(Locale.class)))
                .thenReturn("Classe non trouvée");


        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> classService.deleteClass(1L));


        assertEquals("Classe non trouvée", exception.getMessage());
    }


    @Test
    void updateClassOK() {
        when(classRepository.findById(anyLong())).thenReturn(Optional.of(getClassEntity()));
        when(classRepository.save(any())).thenReturn(getClassEntity());
        when(classMapper.toClassDtoResponse(any())).thenReturn(getClassDtoResponse());


        Optional<ClassDtoResponse> updatedClass = classService.updateClass(1L, getClassDtoRequest());


        assertTrue(updatedClass.isPresent());
        assertEquals("Terminale S", updatedClass.get().getName());
    }


    @Test
    void updateClassKO() {
        when(classRepository.findById(anyLong())).thenReturn(Optional.empty());
        when(messageSource.getMessage(eq("class.notfound"), any(), any(Locale.class)))
                .thenReturn("Classe non trouvée");


        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> classService.updateClass(1L, getClassDtoRequest()));


        assertEquals("Classe non trouvée", exception.getMessage());
    }


    private ClassDtoRequest getClassDtoRequest() {
        ClassDtoRequest classDtoRequest = new ClassDtoRequest();
        classDtoRequest.setName("Terminale S");
        classDtoRequest.setDescription("Classe de terminale scientifique");
        classDtoRequest.setArchive(false);


        return classDtoRequest;
    }


    private ClassEntity getClassEntity() {
        ClassEntity classEntity = new ClassEntity();
        classEntity.setId(1L);
        classEntity.setName("Terminale S");
        classEntity.setDescription("Classe de terminale scientifique");
        classEntity.setArchive(false);


        return classEntity;
    }


    private ClassDtoResponse getClassDtoResponse() {
        ClassDtoResponse classDtoResponse = new ClassDtoResponse();
        classDtoResponse.setId(1L);
        classDtoResponse.setName("Terminale S");
        classDtoResponse.setDescription("Classe de terminale scientifique");
        classDtoResponse.setArchive(false);


        return classDtoResponse;
    }
}





