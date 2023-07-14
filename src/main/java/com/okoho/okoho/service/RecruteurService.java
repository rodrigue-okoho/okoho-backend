package com.okoho.okoho.service;

import com.okoho.okoho.service.dto.RecruteurDTO;
import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing {@link com.okoho.okoho.domain.Recruteur}.
 */
public interface RecruteurService {
    /**
     * Save a recruteur.
     *
     * @param recruteurDTO the entity to save.
     * @return the persisted entity.
     */
    RecruteurDTO save(RecruteurDTO recruteurDTO);

    /**
     * Partially updates a recruteur.
     *
     * @param recruteurDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<RecruteurDTO> partialUpdate(RecruteurDTO recruteurDTO);

    /**
     * Get all the recruteurs.
     *
     * @return the list of entities.
     */
    List<RecruteurDTO> findAll();

    /**
     * Get the "id" recruteur.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<RecruteurDTO> findOne(String id);

    /**
     * Delete the "id" recruteur.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
