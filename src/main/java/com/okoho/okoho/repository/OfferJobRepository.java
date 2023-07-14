package com.okoho.okoho.repository;

import com.okoho.okoho.domain.OfferJob;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * Spring Data MongoDB repository for the OfferJob entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OfferJobRepository extends MongoRepository<OfferJob, String> {}
