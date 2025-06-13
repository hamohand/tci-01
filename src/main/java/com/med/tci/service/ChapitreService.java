package com.med.tci.service;

import com.med.tci.model.Chapitre;
import com.med.tci.repository.ChapitreRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service class for managing Chapitre entities.
 */
@Service
public class ChapitreService {

    private final ChapitreRepository chapitreRepository;

    @Autowired
    public ChapitreService(ChapitreRepository chapitreRepository) {
        this.chapitreRepository = chapitreRepository;
    }

    /**
     * Get all chapters.
     *
     * @return list of all chapters
     */
    public List<Chapitre> getAllChapitres() {
        return chapitreRepository.findAll();
    }

    /**
     * Get a chapter by its ID.
     *
     * @param id the chapter ID
     * @return an Optional containing the chapter if found
     */
    public Optional<Chapitre> getChapitreById(Long id) {
        return chapitreRepository.findById(id);
    }

    /**
     * Get a chapter by its code.
     *
     * @param code the chapter code
     * @return an Optional containing the chapter if found
     */
    public Optional<Chapitre> getChapitreByCode(String code) {
        return chapitreRepository.findByCode(code);
    }

    /**
     * Get chapters by section.
     *
     * @param section the section number
     * @return list of chapters belonging to the section
     */
    public List<Chapitre> getChapitresBySection(String section) {
        return chapitreRepository.findBySection(section);
    }

    /**
     * Save a new chapter or update an existing one.
     *
     * @param chapitre the chapter to save
     * @return the saved chapter
     */
    @Transactional
    public Chapitre saveChapitre(Chapitre chapitre) {
        return chapitreRepository.save(chapitre);
    }

    /**
     * Delete a chapter by its ID.
     *
     * @param id the chapter ID
     */
    @Transactional
    public void deleteChapitre(Long id) {
        chapitreRepository.deleteById(id);
    }

    /**
     * Check if a chapter with the given code exists.
     *
     * @param code the chapter code
     * @return true if a chapter with the code exists, false otherwise
     */
    public boolean existsByCode(String code) {
        return chapitreRepository.existsByCode(code);
    }
}