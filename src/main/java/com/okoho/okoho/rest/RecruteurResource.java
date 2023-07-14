package com.okoho.okoho.rest;

import com.okoho.okoho.repository.RecruteurRepository;
import com.okoho.okoho.service.RecruteurService;
import com.okoho.okoho.service.dto.RecruteurDTO;
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
 * REST controller for managing {@link com.okoho.okoho.domain.Recruteur}.
 */
@RestController
@RequestMapping("/api")
public class RecruteurResource {

    private final Logger log = LoggerFactory.getLogger(RecruteurResource.class);

    private static final String ENTITY_NAME = "okohoRecruteur";

    @Value("${okoho.clientApp.name}")
    private String applicationName;

    private final RecruteurService recruteurService;

    private final RecruteurRepository recruteurRepository;

    public RecruteurResource(RecruteurService recruteurService, RecruteurRepository recruteurRepository) {
        this.recruteurService = recruteurService;
        this.recruteurRepository = recruteurRepository;
    }

    /**
     * {@code POST  /recruteurs} : Create a new recruteur.
     *
     * @param recruteurDTO the recruteurDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new recruteurDTO, or with status {@code 400 (Bad Request)} if the recruteur has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     * @throws BadRequestAlertException
     */
    @PostMapping("/recruteurs")
    public ResponseEntity<RecruteurDTO> createRecruteur(@RequestBody RecruteurDTO recruteurDTO) throws URISyntaxException, BadRequestAlertException {
        log.debug("REST request to save Recruteur : {}", recruteurDTO);
        if (recruteurDTO.getId() != null) {
            throw new BadRequestAlertException("A new recruteur cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RecruteurDTO result = recruteurService.save(recruteurDTO);
        return ResponseEntity
            .created(new URI("/api/recruteurs/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, false, ENTITY_NAME, result.getId()))
            .body(result);
    }

    /**
     * {@code PUT  /recruteurs/:id} : Updates an existing recruteur.
     *
     * @param id the id of the recruteurDTO to save.
     * @param recruteurDTO the recruteurDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recruteurDTO,
     * or with status {@code 400 (Bad Request)} if the recruteurDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the recruteurDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     * @throws BadRequestAlertException
     */
    @PutMapping("/recruteurs/{id}")
    public ResponseEntity<RecruteurDTO> updateRecruteur(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody RecruteurDTO recruteurDTO
    ) throws URISyntaxException, BadRequestAlertException {
        log.debug("REST request to update Recruteur : {}, {}", id, recruteurDTO);
        if (recruteurDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, recruteurDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!recruteurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        RecruteurDTO result = recruteurService.save(recruteurDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, recruteurDTO.getId()))
            .body(result);
    }

    /**
     * {@code PATCH  /recruteurs/:id} : Partial updates given fields of an existing recruteur, field will ignore if it is null
     *
     * @param id the id of the recruteurDTO to save.
     * @param recruteurDTO the recruteurDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated recruteurDTO,
     * or with status {@code 400 (Bad Request)} if the recruteurDTO is not valid,
     * or with status {@code 404 (Not Found)} if the recruteurDTO is not found,
     * or with status {@code 500 (Internal Server Error)} if the recruteurDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     * @throws BadRequestAlertException
     */
    @PatchMapping(value = "/recruteurs/{id}", consumes = "application/merge-patch+json")
    public ResponseEntity<RecruteurDTO> partialUpdateRecruteur(
        @PathVariable(value = "id", required = false) final String id,
        @RequestBody RecruteurDTO recruteurDTO
    ) throws URISyntaxException, BadRequestAlertException {
        log.debug("REST request to partial update Recruteur partially : {}, {}", id, recruteurDTO);
        if (recruteurDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, recruteurDTO.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!recruteurRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<RecruteurDTO> result = recruteurService.partialUpdate(recruteurDTO);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, false, ENTITY_NAME, recruteurDTO.getId())
        );
    }

    /**
     * {@code GET  /recruteurs} : get all the recruteurs.
     *
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of recruteurs in body.
     */
    @GetMapping("/recruteurs")
    public List<RecruteurDTO> getAllRecruteurs() {
        log.debug("REST request to get all Recruteurs");
        return recruteurService.findAll();
    }

    /**
     * {@code GET  /recruteurs/:id} : get the "id" recruteur.
     *
     * @param id the id of the recruteurDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the recruteurDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/recruteurs/{id}")
    public ResponseEntity<RecruteurDTO> getRecruteur(@PathVariable String id) {
        log.debug("REST request to get Recruteur : {}", id);
        Optional<RecruteurDTO> recruteurDTO = recruteurService.findOne(id);
        return ResponseUtil.wrapOrNotFound(recruteurDTO);
    }

    /**
     * {@code DELETE  /recruteurs/:id} : delete the "id" recruteur.
     *
     * @param id the id of the recruteurDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/recruteurs/{id}")
    public ResponseEntity<Void> deleteRecruteur(@PathVariable String id) {
        log.debug("REST request to delete Recruteur : {}", id);
        recruteurService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, false, ENTITY_NAME, id)).build();
    }
}
