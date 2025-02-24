package com.yourispen.studentms.students.controller;

import com.yourispen.studentms.students.dto.requests.StudentDtoRequest;
import com.yourispen.studentms.students.dto.responses.StudentDtoResponse;
import com.yourispen.studentms.students.services.StudentService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
@AllArgsConstructor
@Getter
@Setter
//@CrossOrigin(value = "http://localhost:3000")
public class StudentController {

    private final StudentService studentService;

    /**
     * Ajouter un nouvel étudiant.
     * @param studentDto Les informations de l'étudiant à ajouter.
     * @return ResponseEntity contenant l'étudiant ajouté et le statut HTTP 201.
     */
    @PostMapping
    public ResponseEntity<StudentDtoResponse> saveStudent(@RequestBody @Valid StudentDtoRequest studentDto) {
        Optional<StudentDtoResponse> savedStudent = studentService.saveStudent(studentDto);
        return new ResponseEntity<>(savedStudent.get(), HttpStatus.CREATED);
    }

    /**
     * Modifier les informations d'un étudiant.
     * @param id L'ID de l'étudiant à modifier.
     * @param studentDto Les nouvelles informations de l'étudiant.
     * @return ResponseEntity contenant l'étudiant mis à jour et le statut HTTP 200.
     */
    @PutMapping("/{id}")
    public ResponseEntity<StudentDtoResponse> updateStudent(
            @PathVariable("id") Long id, @RequestBody @Valid StudentDtoRequest studentDto) {
        Optional<StudentDtoResponse> updatedStudent = studentService.updateStudent(id, studentDto);
        return new ResponseEntity<>(updatedStudent.get(), HttpStatus.OK);
    }

    /**
     * Récupérer un étudiant par son ID.
     * @param id L'ID de l'étudiant recherché.
     * @return ResponseEntity contenant l'étudiant trouvé et le statut HTTP 200.
     */
    @GetMapping("/{id}")
    public ResponseEntity<StudentDtoResponse> getStudent(@PathVariable("id") Long id) {
        Optional<StudentDtoResponse> student = studentService.getStudentById(id);
        return student.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /**
     * Récupérer la liste de tous les étudiants.
     * @return ResponseEntity contenant la liste des étudiants et le statut HTTP 200.
     */
    @GetMapping
    public ResponseEntity<List<StudentDtoResponse>> allStudents() {
        List<StudentDtoResponse> students = studentService.getAllStudents();
        return ResponseEntity.ok(students);
    }

    /**
     * Supprimer un étudiant par son ID.
     * @param id L'ID de l'étudiant à supprimer.
     * @return ResponseEntity avec le statut HTTP 204 si la suppression est réussie.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable("id") Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.noContent().build();
    }
}



