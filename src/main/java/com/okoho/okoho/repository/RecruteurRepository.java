package com.okoho.okoho.repository;

import com.okoho.okoho.domain.Recruteur;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the Recruteur entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RecruteurRepository extends MongoRepository<Recruteur, String> {}
