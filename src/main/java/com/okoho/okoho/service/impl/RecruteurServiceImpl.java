package com.okoho.okoho.service.impl;

import com.okoho.okoho.domain.Recruteur;
import com.okoho.okoho.repository.RecruteurRepository;
import com.okoho.okoho.service.RecruteurService;
import com.okoho.okoho.service.dto.RecruteurDTO;
import com.okoho.okoho.service.mapper.RecruteurMapper;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Service Implementation for managing {@link Recruteur}.
 */
@Service
public class RecruteurServiceImpl implements RecruteurService {

    private final Logger log = LoggerFactory.getLogger(RecruteurServiceImpl.class);

    private final RecruteurRepository recruteurRepository;

    private final RecruteurMapper recruteurMapper;

    public RecruteurServiceImpl(RecruteurRepository recruteurRepository, RecruteurMapper recruteurMapper) {
        this.recruteurRepository = recruteurRepository;
        this.recruteurMapper = recruteurMapper;
    }

    @Override
    public RecruteurDTO save(RecruteurDTO recruteurDTO) {
        log.debug("Request to save Recruteur : {}", recruteurDTO);
        Recruteur recruteur = recruteurMapper.toEntity(recruteurDTO);
        recruteur = recruteurRepository.save(recruteur);
        return recruteurMapper.toDto(recruteur);
    }

    @Override
    public Optional<RecruteurDTO> partialUpdate(RecruteurDTO recruteurDTO) {
        log.debug("Request to partially update Recruteur : {}", recruteurDTO);

        return recruteurRepository
            .findById(recruteurDTO.getId())
            .map(
                existingRecruteur -> {
                    recruteurMapper.partialUpdate(existingRecruteur, recruteurDTO);

                    return existingRecruteur;
                }
            )
            .map(recruteurRepository::save)
            .map(recruteurMapper::toDto);
    }

    @Override
    public List<RecruteurDTO> findAll() {
        log.debug("Request to get all Recruteurs");
        return recruteurRepository.findAll().stream().map(recruteurMapper::toDto).collect(Collectors.toCollection(LinkedList::new));
    }

    @Override
    public Optional<RecruteurDTO> findOne(String id) {
        log.debug("Request to get Recruteur : {}", id);
        return recruteurRepository.findById(id).map(recruteurMapper::toDto);
    }

    @Override
    public void delete(String id) {
        log.debug("Request to delete Recruteur : {}", id);
        recruteurRepository.deleteById(id);
    }
}
