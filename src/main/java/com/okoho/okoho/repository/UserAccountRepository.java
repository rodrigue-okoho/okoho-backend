package com.okoho.okoho.repository;

import com.okoho.okoho.domain.UserAccount;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Spring Data MongoDB repository for the UserAccount entity.
 */

public interface UserAccountRepository extends MongoRepository<UserAccount, String> {
    Optional<UserAccount> findOneByEmail(String username);
    Optional<UserAccount> findOneByEmailIgnoreCase(String email);
}
