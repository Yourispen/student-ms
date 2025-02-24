package com.yourispen.studentms.registrations.controller;


import com.yourispen.studentms.registrations.dto.requests.RegistrationDtoRequest;
import com.yourispen.studentms.registrations.dto.responses.RegistrationDtoResponse;
import com.yourispen.studentms.registrations.services.RegistrationService;
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
@RequestMapping("/registrations")
@AllArgsConstructor
@Getter
@Setter
//@CrossOrigin(value = "http://localhost:3000")
public class RegistrationController {


    private final RegistrationService registrationService;


    /**
     * Ajouter une nouvelle inscription.
     * @param registrationDto Les informations de l'inscription.
     * @return ResponseEntity contenant l'inscription ajoutée.
     */
    @PostMapping
    public ResponseEntity<RegistrationDtoResponse> saveRegistration(
            @RequestBody @Valid RegistrationDtoRequest registrationDto) {
        Optional<RegistrationDtoResponse> savedRegistration = registrationService.saveRegistration(registrationDto);
        return savedRegistration.map(response -> new ResponseEntity<>(response, HttpStatus.CREATED))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.BAD_REQUEST));
    }


    /**
     * Modifier une inscription existante.
     * @param id L'ID de l'inscription.
     * @param registrationDto Les nouvelles données.
     * @return ResponseEntity contenant l'inscription mise à jour.
     */
    @PutMapping("/{id}")
    public ResponseEntity<RegistrationDtoResponse> updateRegistration(
            @PathVariable("id") Long id, @RequestBody @Valid RegistrationDtoRequest registrationDto) {
        Optional<RegistrationDtoResponse> updatedRegistration = registrationService.updateRegistration(id, registrationDto);
        return updatedRegistration.map(response -> new ResponseEntity<>(response, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    /**
     * Récupérer une inscription par son ID.
     * @param id L'ID de l'inscription.
     * @return ResponseEntity contenant l'inscription trouvée.
     */
    @GetMapping("/{id}")
    public ResponseEntity<RegistrationDtoResponse> getRegistration(@PathVariable("id") Long id) {
        Optional<RegistrationDtoResponse> registration = registrationService.getRegistrationById(id);
        return registration.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    /**
     * Supprimer une inscription par son ID.
     * @param id L'ID de l'inscription à supprimer.
     * @return ResponseEntity avec statut HTTP 204 si suppression réussie.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRegistration(@PathVariable("id") Long id) {
        registrationService.deleteRegistration(id);
        return ResponseEntity.noContent().build();
    }


    /**
     * Récupérer toutes les inscriptions.
     * @return ResponseEntity contenant la liste des inscriptions.
     */
    @GetMapping
    public ResponseEntity<List<RegistrationDtoResponse>> allRegistrations() {
        List<RegistrationDtoResponse> registrations = registrationService.getAllRegistrations();
        return ResponseEntity.ok(registrations);
    }
}
