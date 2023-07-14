package com.okoho.okoho.service;

import com.okoho.okoho.domain.Candidat;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link Candidat}.
 */
public interface CandidatService {
    /**
     * Save a candidat.
     *
     * @param candidat the entity to save.
     * @return the persisted entity.
     */
    Candidat save(Candidat candidat);

    /**
     * Partially updates a candidat.
     *
     * @param candidat the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Candidat> partialUpdate(Candidat candidat);

    /**
     * Get all the candidats.
     *
     * @return the list of entities.
     */
    List<Candidat> findAll();

    /**
     * Get all the candidats with eager load of many-to-many relationships.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Candidat> findAllWithEagerRelationships(Pageable pageable);

    /**
     * Get the "id" candidat.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Candidat> findOne(String id);

    /**
     * Delete the "id" candidat.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
