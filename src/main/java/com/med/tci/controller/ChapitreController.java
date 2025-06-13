package com.med.tci.controller;

import com.med.tci.model.Chapitre;
import com.med.tci.repository.ChapitreRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/chapitres")
public class ChapitreController {

    private final ChapitreRepository chapitreRepository;

    public ChapitreController(ChapitreRepository chapitreRepository) {
        this.chapitreRepository = chapitreRepository;
    }

    // Get all chapitres
    @GetMapping
    public List<Chapitre> getAllChapitres() {
        return chapitreRepository.findAll();
    }

    // Get a chapitre by id
    @GetMapping("/{id}")
    public ResponseEntity<Chapitre> getChapitreById(@PathVariable Long id) {
        Optional<Chapitre> chapitre = chapitreRepository.findById(id);
        return chapitre.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Get a chapitre by code
    @Operation(summary = "Rechercher un chapitre par son code")
    @ApiResponse(responseCode = "200", description = "Chapitre trouvé")
    @ApiResponse(responseCode = "404", description = "Chapitre non trouvé")
    @GetMapping("/code/{code}")
    public ResponseEntity<Chapitre> rechercherChapitreParCode(@PathVariable String code) {
        return convertirEnResponseEntity(chapitreRepository.findByCodeTrimmed(code));
    }

    private ResponseEntity<Chapitre> convertirEnResponseEntity(Optional<Chapitre> chapitre) {
        return chapitre.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    // Create a new chapitre
    @PostMapping
    public ResponseEntity<Chapitre> createChapitre(@RequestBody Chapitre chapitre) {
        Chapitre savedChapitre = chapitreRepository.save(chapitre);
        return new ResponseEntity<>(savedChapitre, HttpStatus.CREATED);
    }

    // Update a chapitre
    @PutMapping("/{id}")
    public ResponseEntity<Chapitre> updateChapitre(@PathVariable Long id, @RequestBody Chapitre chapitreDetails) {
        return chapitreRepository.findById(id)
                .map(existingChapitre -> {
                    existingChapitre.setCode(chapitreDetails.getCode());
                    existingChapitre.setDescription(chapitreDetails.getDescription());
                    existingChapitre.setSection(chapitreDetails.getSection());
                    Chapitre updatedChapitre = chapitreRepository.save(existingChapitre);
                    return ResponseEntity.ok(updatedChapitre);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Delete a chapitre
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteChapitre(@PathVariable Long id) {
        return chapitreRepository.findById(id)
                .map(chapitre -> {
                    chapitreRepository.delete(chapitre);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

}
