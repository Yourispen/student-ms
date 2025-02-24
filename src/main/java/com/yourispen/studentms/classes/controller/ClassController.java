package com.yourispen.studentms.classes.controller;


import com.yourispen.studentms.classes.dto.requests.ClassDtoRequest;
import com.yourispen.studentms.classes.dto.responses.ClassDtoResponse;
import com.yourispen.studentms.classes.services.ClassService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/classes")
@AllArgsConstructor
//@CrossOrigin(value = "http://localhost:3000")
public class ClassController {


    private final ClassService classService;


    @PostMapping
    public ResponseEntity<ClassDtoResponse> saveClass(@RequestBody @Valid ClassDtoRequest classDto) {
        Optional<ClassDtoResponse> savedClass = classService.saveClass(classDto);
        return new ResponseEntity<>(savedClass.get(), HttpStatus.CREATED);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ClassDtoResponse> getClass(@PathVariable("id") Long id) {
        Optional<ClassDtoResponse> classResponse = classService.getClassById(id);
        return classResponse.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @GetMapping
    public ResponseEntity<List<ClassDtoResponse>> getAllClasses() {
        List<ClassDtoResponse> classes = classService.getAllClasses();
        return ResponseEntity.ok(classes);
    }


    @PutMapping("/{id}")
    public ResponseEntity<ClassDtoResponse> updateClass(
            @PathVariable("id") Long id, @RequestBody @Valid ClassDtoRequest classDto) {
        Optional<ClassDtoResponse> updatedClass = classService.updateClass(id, classDto);
        return new ResponseEntity<>(updatedClass.get(), HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClass(@PathVariable("id") Long id) {
        classService.deleteClass(id);
        return ResponseEntity.noContent().build();
    }
}
