package com.okoho.okoho.service;

import com.okoho.okoho.service.dto.OfferJobDTO;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.okoho.okoho.domain.OfferJob}.
 */
public interface OfferJobService {
    /**
     * Save a offerJob.
     *
     * @param offerJobDTO the entity to save.
     * @return the persisted entity.
     */
    OfferJobDTO save(OfferJobDTO offerJobDTO);

    /**
     * Partially updates a offerJob.
     *
     * @param offerJobDTO the entity to update partially.
     * @return the persisted entity.
     */
    Optional<OfferJobDTO> partialUpdate(OfferJobDTO offerJobDTO);

    /**
     * Get all the offerJobs.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<OfferJobDTO> findAll(Pageable pageable);

    /**
     * Get the "id" offerJob.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<OfferJobDTO> findOne(String id);

    /**
     * Delete the "id" offerJob.
     *
     * @param id the id of the entity.
     */
    void delete(String id);
}
