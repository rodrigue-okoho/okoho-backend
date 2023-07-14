package com.okoho.okoho.service.impl;

import com.okoho.okoho.domain.Candidat;
import com.okoho.okoho.repository.CandidatRepository;
import com.okoho.okoho.service.CandidatService;
import java.util.List;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Candidat}.
 */
@Service
public class CandidatServiceImpl implements CandidatService {

    private final Logger log = LoggerFactory.getLogger(CandidatServiceImpl.class);

    private final CandidatRepository candidatRepository;

    public CandidatServiceImpl(CandidatRepository candidatRepository) {
        this.candidatRepository = candidatRepository;
    }

    @Override
    public Candidat save(Candidat candidat) {
        log.debug("Request to save Candidat : {}", candidat);
        return candidatRepository.save(candidat);
    }

    @Override
    public Optional<Candidat> partialUpdate(Candidat candidat) {
        log.debug("Request to partially update Candidat : {}", candidat);

        return candidatRepository
            .findById(candidat.getId())
            .map(
                existingCandidat -> {
                    if (candidat.getCountry() != null) {
                        existingCandidat.setCountry(candidat.getCountry());
                    }
                    if (candidat.getDob() != null) {
                        existingCandidat.setDob(candidat.getDob());
                    }

                    return existingCandidat;
                }
            )
            .map(candidatRepository::save);
    }

    @Override
    public List<Candidat> findAll() {
        log.debug("Request to get all Candidats");
        return candidatRepository.findAllWithEagerRelationships();
    }

    public Page<Candidat> findAllWithEagerRelationships(Pageable pageable) {
        return candidatRepository.findAllWithEagerRelationships(pageable);
    }

    @Override
    public Optional<Candidat> findOne(String id) {
        log.debug("Request to get Candidat : {}", id);
        return candidatRepository.findOneWithEagerRelationships(id);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Candidat : {}", id);
        candidatRepository.deleteById(id);
    }
}
