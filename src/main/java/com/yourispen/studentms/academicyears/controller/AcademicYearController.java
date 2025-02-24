package com.yourispen.studentms.academicyears.controller;


import com.yourispen.studentms.academicyears.dto.requests.AcademicYearDtoRequest;
import com.yourispen.studentms.academicyears.dto.responses.AcademicYearDtoResponse;
import com.yourispen.studentms.academicyears.services.AcademicYearService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/academic-years")
@AllArgsConstructor
//@CrossOrigin(value = "http://localhost:3000")
public class AcademicYearController {


    private final AcademicYearService academicYearService;


    /**
     * Ajouter une nouvelle année académique.
     * @param request Les informations de l'année académique à ajouter.
     * @return ResponseEntity contenant l'année académique ajoutée et le statut HTTP 201.
     */
    @PostMapping
    public ResponseEntity<AcademicYearDtoResponse> saveAcademicYear(@RequestBody @Valid AcademicYearDtoRequest request) {
        Optional<AcademicYearDtoResponse> savedYear = academicYearService.saveAcademicYear(request);
        return new ResponseEntity<>(savedYear.get(), HttpStatus.CREATED);
    }


    /**
     * Modifier une année académique.
     * @param id L'ID de l'année académique à modifier.
     * @param request Les nouvelles informations de l'année académique.
     * @return ResponseEntity contenant l'année académique mise à jour et le statut HTTP 200.
     */
    @PutMapping("/{id}")
    public ResponseEntity<AcademicYearDtoResponse> updateAcademicYear(
            @PathVariable("id") Long id, @RequestBody @Valid AcademicYearDtoRequest request) {
        Optional<AcademicYearDtoResponse> updatedYear = academicYearService.updateAcademicYear(id, request);
        return new ResponseEntity<>(updatedYear.get(), HttpStatus.OK);
    }


    /**
     * Récupérer une année académique par son ID.
     * @param id L'ID de l'année académique recherchée.
     * @return ResponseEntity contenant l'année académique trouvée et le statut HTTP 200.
     */
    @GetMapping("/{id}")
    public ResponseEntity<AcademicYearDtoResponse> getAcademicYear(@PathVariable("id") Long id) {
        Optional<AcademicYearDtoResponse> year = academicYearService.getAcademicYearById(id);
        return year.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    /**
     * Récupérer la liste de toutes les années académiques.
     * @return ResponseEntity contenant la liste des années académiques et le statut HTTP 200.
     */
    @GetMapping
    public ResponseEntity<List<AcademicYearDtoResponse>> getAllAcademicYears() {
        List<AcademicYearDtoResponse> years = academicYearService.getAllAcademicYears();
        return ResponseEntity.ok(years);
    }


    /**
     * Supprimer une année académique par son ID.
     * @param id L'ID de l'année académique à supprimer.
     * @return ResponseEntity avec le statut HTTP 204 si la suppression est réussie.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAcademicYear(@PathVariable("id") Long id) {
        academicYearService.deleteAcademicYear(id);
        return ResponseEntity.noContent().build();
    }
}
