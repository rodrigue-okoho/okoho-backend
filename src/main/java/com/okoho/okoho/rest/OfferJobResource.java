package com.okoho.okoho.rest;

import com.okoho.okoho.repository.OfferJobRepository;
import com.okoho.okoho.service.OfferJobService;
import com.okoho.okoho.service.dto.OfferJobDTO;
import com.okoho.okoho.utils.HeaderUtil;
import com.okoho.okoho.utils.ResponseUtil;
import com.okoho.okoho.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;


/**
 * REST controller for managing {@link com.okoho.okoho.domain.OfferJob}.
 */
@RestController
@RequestMapping("/api")
public class OfferJobResource {

    private final Logger log = LoggerFactory.getLogger(OfferJobResource.class);

    private static final String ENTITY_NAME = "okohoOfferJob";

    @Value("${okoho.clientApp.name}")
    private String applicationName;

    private final OfferJobService offerJobService;

    private final OfferJobRepository offerJobRepository;

    public OfferJobResource(OfferJobService offerJobService, OfferJobRepository offerJobRepository) {
        this.offerJobService = offerJobService;
        this.offerJobRepository = offerJobRepository;
    }

    /**
     * {@code POST  /offer-jobs} : Create a new offerJob.
     *
     * @param offerJobDTO the offerJobDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new offerJobDTO, or with status {@code 400 (Bad Request)} if the offerJob has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     * @throws BadRequestAlertException
     */
    @PostMapping("/offer-jobs")
    public ResponseEntity<OfferJobDTO> createOfferJob(@RequestBody OfferJobDTO offerJobDTO) throws URISyntaxException, BadRequestAlertException {
        log.debug("REST request to save OfferJob : {}", offerJobDTO);
        if (offerJobDTO.getId() != null) {
            throw new BadRequestAlertException("A new offerJob cannot already have an ID", ENTITY_NAME, "idexists");
        }
        OfferJobDTO result = offerJobService.save(offerJobDTO);
        return ResponseEntity
            .created(new URI("/api/offer-jobs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /offer-jobs/:id} : Updates an existing offerJob.
     *
     * @param id the id of the offerJobDTO to save.
     * @param offerJobDTO the offerJobDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated offerJobDTO,
     * or with status {@code 400 (Bad Request)} if the offerJobDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the offerJobDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     * @throws BadRequestAlertException
     */
    @PutMapping("/offer-jobs/{id}")
    public ResponseEntity<OfferJobDTO> updateOfferJob(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody OfferJobDTO offerJobDTO
    ) throws URISyntaxException, BadRequestAlertException {
        log.debug("REST request to update OfferJob : {}, {}", id, offerJobDTO);
        if (offerJobDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, offerJobDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!offerJobRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        OfferJobDTO result = offerJobService.save(offerJobDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, offerJobDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /offer-jobs/:id} : Partial updates given fields of an existing offerJob, field will ignore if it is null
     *
     * @param id the id of the offerJobDTO to save.
     * @param offerJobDTO the offerJobDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated offerJobDTO,
     * or with status {@code 400 (Bad Request)} if the offerJobDTO is not valid,
     * or with status {@code 404 (Not Found)} if the offerJobDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the offerJobDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     * @throws BadRequestAlertException
     */
    @PatchMapping(value = "/offer-jobs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<OfferJobDTO> partialUpdateOfferJob(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody OfferJobDTO offerJobDTO
    ) throws URISyntaxException, BadRequestAlertException {
        log.debug("REST request to partial update OfferJob partially : {}, {}", id, offerJobDTO);
        if (offerJobDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, offerJobDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!offerJobRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<OfferJobDTO> result = offerJobService.partialUpdate(offerJobDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, offerJobDTO.getId())
        );
    }

    /**
     * {@code GET  /offer-jobs} : get all the offerJobs.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of offerJobs in body.
     */
    @GetMapping("/offer-jobs")
    public ResponseEntity<List<OfferJobDTO>> getAllOfferJobs(Pageable pageable) {
        log.debug("REST request to get a page of OfferJobs");
        Page<OfferJobDTO> page = offerJobService.findAll(pageable);
     //   HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().body(page.getContent());
    }

    /**
     * {@code GET  /offer-jobs/:id} : get the "id" offerJob.
     *
     * @param id the id of the offerJobDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the offerJobDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/offer-jobs/{id}")
    public ResponseEntity<OfferJobDTO> getOfferJob(@PathVariable String id) {
        log.debug("REST request to get OfferJob : {}", id);
        Optional<OfferJobDTO> offerJobDTO = offerJobService.findOne(id);
        return ResponseUtil.wrapOrNotFound(offerJobDTO);
    }

    /**
     * {@code DELETE  /offer-jobs/:id} : delete the "id" offerJob.
     *
     * @param id the id of the offerJobDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/offer-jobs/{id}")
    public ResponseEntity<Void> deleteOfferJob(@PathVariable String id) {
        log.debug("REST request to delete OfferJob : {}", id);
        offerJobService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
