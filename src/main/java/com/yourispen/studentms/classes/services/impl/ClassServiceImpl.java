package com.yourispen.studentms.classes.services.impl;


import com.yourispen.studentms.classes.dto.requests.ClassDtoRequest;
import com.yourispen.studentms.classes.dto.responses.ClassDtoResponse;
import com.yourispen.studentms.classes.entities.ClassEntity;
import com.yourispen.studentms.classes.mapper.ClassMapper;
import com.yourispen.studentms.classes.repository.ClassRepository;
import com.yourispen.studentms.classes.services.ClassService;
import com.yourispen.studentms.exception.EntityExistsException;
import com.yourispen.studentms.exception.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;
import java.util.Locale;
import java.util.Optional;


@Service
@AllArgsConstructor
public class ClassServiceImpl implements ClassService {


    private static final String CLASS_NOTFOUND = "class.notfound";
    private static final String CLASS_EXISTS = "class.exists";


    private final ClassRepository classRepository;
    private final ClassMapper classMapper;
    private final MessageSource messageSource;


    @Override
    @Transactional
    public Optional<ClassDtoResponse> saveClass(ClassDtoRequest classDto) {
        if (classRepository.findByName(classDto.getName()).isPresent()) {
            throw new EntityExistsException(
                    messageSource.getMessage(CLASS_EXISTS, new Object[]{classDto.getName()}, Locale.getDefault()));
        }


        ClassEntity classEntity = classMapper.toClassEntity(classDto);
        ClassEntity savedClass = classRepository.save(classEntity);
        return Optional.of(classMapper.toClassDtoResponse(savedClass));
    }


    @Override
    public List<ClassDtoResponse> getAllClasses() {
        List<ClassEntity> classes = classRepository.findAll();
        return classMapper.toClassDtoResponseList(classes);
    }


    @Override
    public Optional<ClassDtoResponse> getClassById(Long id) {
        return classRepository.findById(id)
                .map(classMapper::toClassDtoResponse)
                .map(Optional::of)
                .orElseThrow(() -> new EntityNotFoundException(
                        messageSource.getMessage(CLASS_NOTFOUND, new Object[]{id}, Locale.getDefault())));
    }


    @Override
    public boolean deleteClass(Long id) {
        if (classRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException(
                    messageSource.getMessage(CLASS_NOTFOUND, new Object[]{id}, Locale.getDefault()));
        }
        classRepository.deleteById(id);
        return true;
    }


    @Override
    public Optional<ClassDtoResponse> updateClass(Long id, ClassDtoRequest classDto) {
        return classRepository.findById(id)
                .map(classEntity -> {
                    classEntity.setName(classDto.getName());
                    classEntity.setDescription(classDto.getDescription());
                    classEntity.setArchive(classDto.isArchive());


                    ClassEntity updatedClass = classRepository.save(classEntity);
                    return Optional.of(classMapper.toClassDtoResponse(updatedClass));
                }).orElseThrow(() -> new EntityNotFoundException(
                        messageSource.getMessage(CLASS_NOTFOUND, new Object[]{id}, Locale.getDefault())));
    }
}
