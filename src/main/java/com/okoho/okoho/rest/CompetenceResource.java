package com.okoho.okoho.rest;

import com.okoho.okoho.domain.Competence;
import com.okoho.okoho.repository.CompetenceRepository;
import com.okoho.okoho.service.CompetenceService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


/**
 * REST controller for managing {@link com.okoho.okoho.domain.Competence}.
 */
@RestController
@RequestMapping("/api")
public class CompetenceResource {

    private final Logger log = LoggerFactory.getLogger(CompetenceResource.class);

    private static final String ENTITY_NAME = "okohoCompetence";

    @Value("${okoho.clientApp.name}")
    private String applicationName;

    private final CompetenceService competenceService;

    private final CompetenceRepository competenceRepository;

    public CompetenceResource(CompetenceService competenceService, CompetenceRepository competenceRepository) {
        this.competenceService = competenceService;
        this.competenceRepository = competenceRepository;
    }

    /**
     * {@code POST  /competences} : Create a new competence.
     *
     * @param competence the competence to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new competence, or with status {@code 400 (Bad Request)} if the competence has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     * @throws BadRequestAlertException
     */
    @PostMapping("/competences")
    public ResponseEntity<Competence> createCompetence(@RequestBody Competence competence) throws URISyntaxException, BadRequestAlertException {
        log.debug("REST request to save Competence : {}", competence);
        if (competence.getId() != null) {
            throw new BadRequestAlertException("A new competence cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Competence result = competenceService.save(competence);
        return ResponseEntity
            .created(new URI("/api/competences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /competences/:id} : Updates an existing competence.
     *
     * @param id the id of the competence to save.
     * @param competence the competence to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated competence,
     * or with status {@code 400 (Bad Request)} if the competence is not valid,
     * or with status {@code 500 (Internal Server Error)} if the competence couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     * @throws BadRequestAlertException
     */
    @PutMapping("/competences/{id}")
    public ResponseEntity<Competence> updateCompetence(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Competence competence
    ) throws URISyntaxException, BadRequestAlertException {
        log.debug("REST request to update Competence : {}, {}", id, competence);
        if (competence.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, competence.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!competenceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Competence result = competenceService.save(competence);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, competence.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /competences/:id} : Partial updates given fields of an existing competence, field will ignore if it is null
     *
     * @param id the id of the competence to save.
     * @param competence the competence to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated competence,
     * or with status {@code 400 (Bad Request)} if the competence is not valid,
     * or with status {@code 404 (Not Found)} if the competence is not found,
     * or with status {@code 500 (Internal Server Error)} if the competence couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     * @throws BadRequestAlertException
     */
    @PatchMapping(value = "/competences/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<Competence> partialUpdateCompetence(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody Competence competence
    ) throws URISyntaxException, BadRequestAlertException {
        log.debug("REST request to partial update Competence partially : {}, {}", id, competence);
        if (competence.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, competence.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!competenceRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<Competence> result = competenceService.partialUpdate(competence);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, competence.getId())
        );
    }

    /**
     * {@code GET  /competences} : get all the competences.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of competences in body.
     */
    @GetMapping("/competences")
    public List<Competence> getAllCompetences() {
        log.debug("REST request to get all Competences");
        return competenceService.findAll();
    }

    /**
     * {@code GET  /competences/:id} : get the "id" competence.
     *
     * @param id the id of the competence to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the competence, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/competences/{id}")
    public ResponseEntity<Competence> getCompetence(@PathVariable String id) {
        log.debug("REST request to get Competence : {}", id);
        Optional<Competence> competence = competenceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(competence);
    }

    /**
     * {@code DELETE  /competences/:id} : delete the "id" competence.
     *
     * @param id the id of the competence to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/competences/{id}")
    public ResponseEntity<Void> deleteCompetence(@PathVariable String id) {
        log.debug("REST request to delete Competence : {}", id);
        competenceService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
