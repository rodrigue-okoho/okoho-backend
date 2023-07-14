package com.okoho.okoho.service.impl;

import com.okoho.okoho.domain.OfferJob;
import com.okoho.okoho.repository.OfferJobRepository;
import com.okoho.okoho.service.OfferJobService;
import com.okoho.okoho.service.dto.OfferJobDTO;
import com.okoho.okoho.service.mapper.OfferJobMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link OfferJob}.
 */
@Service
public class OfferJobServiceImpl implements OfferJobService {

    private final Logger log = LoggerFactory.getLogger(OfferJobServiceImpl.class);

    private final OfferJobRepository offerJobRepository;

    private final OfferJobMapper offerJobMapper;

    public OfferJobServiceImpl(OfferJobRepository offerJobRepository, OfferJobMapper offerJobMapper) {
        this.offerJobRepository = offerJobRepository;
        this.offerJobMapper = offerJobMapper;
    }

    @Override
    public OfferJobDTO save(OfferJobDTO offerJobDTO) {
        log.debug("Request to save OfferJob : {}", offerJobDTO);
        OfferJob offerJob = offerJobMapper.toEntity(offerJobDTO);
        offerJob = offerJobRepository.save(offerJob);
        return offerJobMapper.toDto(offerJob);
    }

    @Override
    public Optional<OfferJobDTO> partialUpdate(OfferJobDTO offerJobDTO) {
        log.debug("Request to partially update OfferJob : {}", offerJobDTO);

        return offerJobRepository
            .findById(offerJobDTO.getId())
            .map(
                existingOfferJob -> {
                    offerJobMapper.partialUpdate(existingOfferJob, offerJobDTO);

                    return existingOfferJob;
                }
            )
            .map(offerJobRepository::save)
            .map(offerJobMapper::toDto);
    }

    @Override
    public Page<OfferJobDTO> findAll(Pageable pageable) {
        log.debug("Request to get all OfferJobs");
        return offerJobRepository.findAll(pageable).map(offerJobMapper::toDto);
    }

    @Override
    public Optional<OfferJobDTO> findOne(String id) {
        log.debug("Request to get OfferJob : {}", id);
        return offerJobRepository.findById(id).map(offerJobMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete OfferJob : {}", id);
        offerJobRepository.deleteById(id);
    }
}
